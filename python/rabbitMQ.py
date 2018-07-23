rabbitMQ库模块安装:
easy_install   pika


  ##############################       生产者基本模板       ##############################   
import pika
import sys

credentials = pika.PlainCredentials("admin", "1qaz2wsx")
conn_params = pika.ConnectionParameters("47.75.50.16", credentials=credentials)
conn_broker = pika.BlockingConnection(conn_params)

channel = conn_broker.channel()

channel.exchange_declare(exchange="hello-exchange",
                         exchange_type="direct",
                         passive=False,
                         durable=True,
                         auto_delete=False)

msg=sys.argv[1]
msg_props = pika.BasicProperties()
msg_props.content_type = "text/plain"

channel.basic_publish(body=msg,
                      exchange="hello-exchange",
                      properties=msg_props,
                      routing_key="hola")


  ##############################       消费者基本模板       ##############################   
import pika


def msg_consumer(c_channel, method, header, body):
    channel.basic_ack(delivery_tag=method.delivery_tag)
    if body == "quit":
        c_channel.basic_cancel(consumer_tag="hello-consumer")
        c_channel.stop_consuming()
    else:
        print body


credentials = pika.PlainCredentials("admin", "1qaz2wsx")
conn_params = pika.ConnectionParameters("47.75.50.16", credentials=credentials)
conn_broker = pika.BlockingConnection(conn_params)

channel = conn_broker.channel()

channel.exchange_declare(exchange="hello-exchange", exchange_type="direct", passive=False, durable=True, auto_delete=False)

channel.queue_declare(queue="hello-queue")
channel.queue_bind(queue="hello-queue", exchange="hello-exchange", routing_key="hola")

channel.basic_consume(msg_consumer, queue="hello-queue", consumer_tag="hello-consumer")
channel.start_consuming()



  ##############################       告警日志生产者基本模板       ##############################   

import json, pika
from optparse import OptionParser

if __name__ == "__main__":
	AMPQ_SERVER = "47.75.50.16"
	AMPQ_USER = "admin"
	AMPQ_PASS = "1qaz2wsx"
	AMPQ_VHOST = "/"
	AMQP_EXCHANGE = "alerts"

	opt_parser = OptionParser()
	opt_parser.add_option("-r",
	                      "--routing-key",
	                      dest="routing_key",
	                      help="Routing key for message " +
	                      " (e.g. myalert.im)")
	opt_parser.add_option("-m",
	                      "--message",
	                      dest="message",
	                      help="Message text for alert.")
	args = opt_parser.parse_args()[0]
	creds_broker = pika.PlainCredentials(AMPQ_USER, AMPQ_PASS)
	conn_params = pika.ConnectionParameters(AMPQ_SERVER,
	                                        virtual_host=AMPQ_VHOST,
	                                        credentials=creds_broker)
	conn_broker = pika.BlockingConnection(conn_params)
	channel = conn_broker.channel()

	msg = json.dumps(args.message)
	msg_props = pika.BasicProperties()
	msg_props.content_type = "application/json"
	msg_props.durable = False

	channel.basic_publish(body=msg,
	                      exchange=AMQP_EXCHANGE,
	                      properties=msg_props,
	                      routing_key=args.routing_key)
	print ("Sent message %s tagged with routing key '%s' to " +
	       "exchange '/'.") % (json.dumps(args.message), args.routing_key)

  ##############################       告警日志消费者基本模板       ##############################   
import json
import smtplib
import pika


def send_email(recipients, subject, message):
    headers = ("From: %s\r\nTo: \r\nDate: \r\n" + \
               "Subject: %s\r\n\r\n") % ("fegnxing581@gmail.com", subject)

    smtp_server = smtplib.SMTP()
    smtp_server.connect("fengxing581@163.com", 25)
    smtp_server.sendmail("fegnxing581@qq.com", recipients,
                         headers + str(message))
    smtp_server.close()


def critical_notify(channel, method, header, body):
    EMAIL_RECIPS = ["fengxing581@163.com",]

    message = json.loads(body)
    send_email(EMAIL_RECIPS, "CRITICAL ALERT!", message)
    print ("Send alert via e-mail! Alert Text: %s " + \
           "Recipients: %s" % (str(message), str(EMAIL_RECIPS)))
    channel.basic_ack(delivery_tag=method.delivery_tag)


def rate_limit_notify(channel, method, header, body):
    EMAIL_RECIPS = ["fengxing581@qq.com"]

    message = json.loads(body)
    send_email(EMAIL_RECIPS, "RATE LIMIT ALERT!", message)

    print ("Sent alert via e-mail! Alert text: %s " + \
           "Recipients: %s") % (str(message), str(EMAIL_RECIPS))
    channel.basic_ack(delivery_tag=method.delivery_tag)


if __name__ == "__main__":
    AMPQ_SERVER = "47.75.50.16"
    AMPQ_USER = "admin"
    AMPQ_PASS = "1qaz2wsx"
    AMPQ_VHOST = "/"
    AMQP_EXCHANGE = "alerts"

    creds_broker = pika.PlainCredentials(AMPQ_USER, AMPQ_PASS)
    conn_params = pika.ConnectionParameters(AMPQ_SERVER,
                                            virtual_host=AMPQ_VHOST,
                                            credentials=creds_broker)
    conn_broker = pika.BlockingConnection(conn_params)
    channel = conn_broker.channel()

    channel.exchange_declare(exchange=AMQP_EXCHANGE,
                             exchange_type="topic",
                             auto_delete=False)
    channel.queue_declare(queue="critical", auto_delete=False)
    channel.queue_bind(queue="critical",
                       exchange="alerts",
                       routing_key="critical.*")

    channel.queue_declare(queue="rate_limit", auto_delete=False)
    channel.queue_bind(queue="rate_limit",
                       exchange="alerts",
                       routing_key="*.rate_limit")

    channel.basic_consume(critical_notify,
                          queue="critical",
                          no_ack=False,
                          consumer_tag="critical")
    channel.basic_consume(rate_limit_notify,
                          queue="rate_limit",
                          no_ack=False,
                          consumer_tag="rate_limit")
    print "Ready for alerts!"
    channel.start_consuming()


