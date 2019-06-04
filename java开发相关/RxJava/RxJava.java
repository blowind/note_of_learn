



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

