java -Xmx=256M MyProg                 ##  设置JVM可扩展的最大堆栈内存
java -Xms=256M MyProg                 ##  设置JVM启动时堆栈内存的大小

export JAVA_OPTS="-Xmx256M"           ##  linux配置Java运行时内存
set JAVA_OPTS="-Xmx256M"              ##  windows配置Java运行时内存


iptables -t nat -L                ##  检查iptables特性是否enabled
iptables -t nat -I PREROUTING -p tcp -dport 80 -j REDIRECT --to-ports 8080  ## 重定向80端口请求到8080
iptables -t nat -I OUTPUT -p tcp --dport 80 -j REDIRECT --to-ports 8080   ## 重定向80端口请求到8080