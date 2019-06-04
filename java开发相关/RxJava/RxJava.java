
Observable<Tweet> tweets = a;

tweets.subscribe((Tweet tweet) -> System.out.println(tweet));

Observer<Tweet> observer = new Observer<Tweet>() {
	@Override
	public void onNext(Tweet tweet) {
		System.out.println(tweet);
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void onCompleted() {
		noMore();
	}
};



Subscription subscription = tweets.subscribe(System.out::println);
subscription.unsubscribe();

Subscriber<Tweet> subscriber = new Subscriber<Tweet> {
	@Override
	public void onNext(Tweet tweet) {
		if(tweet.getNext().contains("Java")) {
			unsubscribe();
		}
	}

	@Override
	public void onCompleted() {}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
	}
};
tweets.subscribe(subscriber);


Observable.just(value)   // 创建一个给定值的Observable并在被订阅时立即返回该值，然后发送结束事件，重载函数可以输入多个参数
Observable.from(values)  // 同just，但是入参类型是Iterable<T>或者T[]，重载类型也接受Future<T>参数
Observable.range(from, n)  // 产生从from开始的n个整数，包括from，然后发送结束事件
Observable.empty()   //  产生空集Observable
Observable.never()   //  不产生任何通知事件的Observable，主要用于测试
Observable.error()   //  生成一个错误通知


Observable.range(5, 3).subscribe(i -> { log(i); });
// 使用Observable.create()重写
Observable<Integer> ints = Observable.create(new Observable.OnSubscribe<Integer>() {
	@Override
	public void call(Subscriber<? super Integer> subscriber) {
		subscriber.onNext(5);
		subscriber.onNext(6);
		subscriber.onNext(7);
		subscriber.onCompleted();
	}
});
ints.subscribe(i -> log(i));

Observable.just(T x);
// 使用Observable.create()重写
static <T> Observable<T> just(T x) {
	Observable.create(subscriber -> {
		subscriber.onNext(x);
		subscriber.onCompleted();
	});
}


// cache() 操作符，在第一次subscribe后缓存create执行的所有events、completions、errors结果用于后序subscribe的时候使用
Observable<Integer> ints = Observable.<Integer>create(subscriber -> {
	subscriber.onNext(5);
		subscriber.onNext(6);
		subscriber.onNext(7);
		subscriber.onCompleted();
	}
}
}).cache();

// 无限流的写法，注意三个点：
// 1、单起一个线程专门执行推送的数据源，否则会占用调用的业务线程；
// 2、通过isUnsubscribed()判断外部是否取消，不能不感知；
// 3、通过Subscriptions.create()立即感知外部取消订阅的操作；
Observable<BigInteger> naturalNumbers = Observable.create(subscriber -> {
	Runnable r = () -> {
		sleep(10, SECONDS);

		BigInteger i = ZERO;
		while(!subscriber.isUnsubscribed()) {
			subscriber.onNext(i);
			i = i.add(ONE);
		}
	};
	final Thread thread = new Thead(r);
	thread.start();
	subscriber.add(Subscriptions.create(thread::interrupt));
});

static void sleep(int timeout, TimeUnit unit) {
	try{
		unit.sleep(timeout);
	}catch(InterruptedException ignored) {}
}

Subscription subscription = naturalNumbers.subscribe(x -> log(x));
...
subscription.unsubscribe();

// 处理异常的写法
Observable<Data> rxLoad(int id) {
	return Observable.create(subscriber -> {
		try{
			subscriber.onNext(load(id));
			subscriber.onCompleted();
		}catch(Exception e) {
			subscriber.onError(e);
		}
	});
}
// 使用内置操作符fromCallable()更简练的实现上诉效果
Observable<Data> rxLoad(int id) {
	return Observable.fromCallable(() -> load(id));
}

// 时间操作的两个函数  timer()延时产生一个结果（默认是0）   interval()在指定时间间隔不断产生结果（从0开始不断增长）
Observable.timer(1, TimeUnit.SECONDS).subscribe((Long zero) -> log(zero));
Observable.interval(1_000_000/60, MICROSECONDS).subscribe((Long i) -> log(i));



//  基于callback方式最原始的Java写法，通过往各种Listener中添加回掉匿名类
TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
twitterStream.addListener(new twitter4j.StatusListener() {
	@Override  // 处理状态值
	public void onStatus(Status status) {
		log.info("Status: {}", status);
	}

	@Override  // 处理异常
	public void onException(Exception ex) {
		log.error("Error callback", ex);
	}
});
twitterStream.sample();   // 在后台起个线程处理数据，对每个到来的twitter数据调用匿名类对应的函数处理
TimeUnit.SECONDS.sleep(10);
twitterStream.shutdown();  // 关闭流，清理所有底层的资源


//  第一步改造，通过分层抽取具体业务处理作为函数方法类型的参数传入，达到业务和调用逻辑分离，复用调用逻辑的目的
//  思想上还是使用Java 8抽取函数的方法
void consume(Consumer<Status> onStatus, Consumer<Exception> onException) {
	TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	twitterStream.addListener(new twitter4j.StatusListener() {
		@Override  // 使用外部传入的函数处理状态值
		public void onStatus(Status status) {
			onStatus.accept(status);
		}

		@Override  // 使用外部传入的函数处理异常
		public void onException(Exception ex) {
			onException.accept(ex);
		}
	});
	twitterStream.sample();
}
// 具体使用
consume(status -> log.info("Status: {}", status),
		ex -> log.error("Error callback", ex));

// 使用Rx的思想改造，订阅式
Observable<Status> observe() {
	return Observable.create(subscriber -> {
		System.out.println("Establishing connection");
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(new twitter4j.StatusListener() {
			@Override  // 使用外部传入的订阅者处理状态
			public void onStatus(Status status) {
				subscriber.onNext(status);
			}

			@Override  // 使用外部传入的订阅者处理异常
			public void onException(Exception ex) {
				subscriber.onError(ex);
			}
		});
		// subscriber.add(Subscriptions.create(twitterStream::shutdown));
		Subscribe.add(Subscriptions.create(() -> {
			System.out.println("Disconnecting");
			twitterStream.shutdown();
		}))
	});
}
// 具体使用
observe().subscribe(status -> log.info("Status: {}", status),
					ex -> log.error("Error callback", ex));



/**
  总共有4中Subject，分别是：PublishSubject, AsyncSubject, BehaviorSubject, ReplaySubject
**/
//  使用rx.subjects.Subject解决一次生成，多次共享订阅场景，该场景会将生产者产生的所有数据发生给消费者
class TwitterSubject {

	private final PublishSubject<Status> subject = PublishSubject.create();

	public TwitterSubject() {
		TwitterStream twitterStream = new TwitterStreamFactory.getInstance();
		twitterStream.addListener(new twitter4j.StatusListener() {
			@Override  // 使用外部传入的订阅者处理状态
			public void onStatus(Status status) {
				subject.onNext(status);
			}

			@Override  // 使用外部传入的订阅者处理异常
			public void onException(Exception ex) {
				subject.onError(ex);
			}
		});
		twitterStream.sample();
	}

	// 按照Observable类型返回Subject对象，使得外部调用可以subscribe订阅
	public Observable<Status> observe() {
		return subject;
	}
}

//  使用户ConnectableObservable解决一次生成，多次共享订阅场景
//  原始写法，会执行两次Establishing connection和Disconnecting
Observable<Status> observable = observe();
Subscription sub1 = observable.subscribe();
System.out.println("Subscribed 1");
Subscription sbu2 = observable.subscribe();
System.out.println("Subscirbed 2");
sub1.unsubscribe();
System.out.println("unsubscribed 1");
sub2.unsubscribe();
System.out.println("unsubscribed 2");

/* 使用 publish().refCount() 达到类似智能指针的计数效果，
*  类似cache()的效果，但是能较好的处理开始和技术的资源分配及释放 
*/
// 此处只执行一次Establishing connection和Disconnecting
Observable<Status> lazy = observable.publish().refCount();  // 别名写法 lazy = observable.share();
System.out.println("Before subscribers"); 
Subscription sub1 = lazy.subscribe();   // 仅在此时执行一次连接
System.out.println("Subscribed 1"); 
Subscription sub2 = lazy.subscribe(); 
System.out.println("Subscribed 2"); 
sub1.unsubscribe(); 
System.out.println("Unsubscribed 1"); 
sub2.unsubscribe();               //  仅在此时断开连接
System.out.println("Unsubscribed 2");


/* 使用publish().connect() 执行未被有效订阅的Observable，
*  只publish()可以生成Observable<Status>类型结果给外部subscribe()，通过connect()触发其运行
*/
ConnectableObservable<Status> published = tweets.publish();
published.connect();

// Spring中具体示例展示
@Configuration
class Config implements ApplicationListener<ContextRefreshedEvent> {
	private final ConnectableObservable<Status> observable = 
					Observable.<Status>create(subscriber -> {
						log.info("Starting");
					}).publish();

	@Bean
	public Observable<Status> observable() {
		return observable;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Connecting");
		observable.connect();     // 仅在Spring Context所有Bean初始化完毕时connect
	}
}
@Component
class Foo {
	@Autowired
	public Foo(Observable<Status> tweets) {
		tweets.subscribe(status -> {   // 初始化Bean对象时，先订阅到Observable
			log.info(status.getText());
		});
		log.info("Subscirbed");
	}
}
@Component
class Bar {
	@Autowired
	public Bar(Observable<Status> tweets {
		tweets.subscribe(status -> {
			log.info(status.getText());
		});
		log.info("Subscirbed");
	})
}


// RxJava的操作符
Observable<String> comments = someFileSource.lines().filter(s -> s.startsWith("#"));
// doOnNext不能改变流中元素，只能获取并做一些打印之类的操作
Observable.just(8, 9, 10)
		.doOnNext(i -> System.out.println("A: " + i))
		.filter(i -> i%3 > 0)
		.doOnNext(i -> System.out.println("B: " + i))
		.map(i -> "#" + i * 10)
		.doOnNext(s -> System.out.println("C: " + s))
		.filter(s -> s.length() < 4)
		.subscribe(s -> System.out.println("D: " + s));

numbers.flatMap(x -> just(x*2));   // 等价于  numbers.map(x -> x*2);
numbers.flatMap(x -> (x != 10) ? just(x) : empty())  //  等价于 numbers.filter(x -> x != 10);

// 一个顾客多个订单的情况，展开最后获取订单资源流
// 写法一
Observable<Order> orders = customers.flatMap(customer -> Observable.from(customer.getOrders()));
// 写法二
Observable<Order> orders = customers.map(Customer::getOrders).flatMap(Observable::from);
// 写法三  要求getOrders()函数是个简单的getter函数而不能是个耗时函数，否则需要改造成返回Observable<Order>
Observable<Order> orders = customers.flatMapIterable(Customer::getOrders);


// 带 过程/异常处理/成功结束 三种回调的flatMap变种，注意入参类型是T，三个回调的返回类型都是Observable<R>
<R> Observable<R> flatMap(Func1<T, Observable<R>> onNext,
						  Func1<Throwable, Observable<R>> onError,
						  Func0<Observable<R>> onCompleted)
// 文件上传场景只关心最终结果不关心上传过程和上传异常的示例，注意三个回调的类型都必须是Observable<Rating>
Observable<Long> upload(UUID id) {}
Observable<Rating> rate(UUID id) {}
upload(id).flatMap(bytes -> Observable.empty(),
					e -> Observable.error(e),
					() -> rate(id));