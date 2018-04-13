package web.listener;

/* 实现 HttpSessionBindingListener 接口的类不需要在web.xml里面进行配置，
   其他实现listener接口的listener需要在web.xml中配置，执行顺序同配置先后数据，详见web.xml  */
public class MyServletContextListener implements ServletContextListener {
	
	private static Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);
	
	public MyServletContextListener()  {}
	
	/* web应用程序初始化时调用，在所有servlet和filter初始化之前 */
	@Override
	public void contextInitialized(ServletContextEvent event) {	
		ServletContext context = event.getServletContext();
		
		/* 获取 servlet context 配置的基本参数 */
		String initParam = context.getInitParameter("initParam");
		logger.warn("获得参数 {}", initParam);
	}
	
	
	/*  该方法在所有servlet和filter都销毁完毕后，servlet上下文关闭时调用  */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.warn("所有bean都销毁完毕");
	}
	
}