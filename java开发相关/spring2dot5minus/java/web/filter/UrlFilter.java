package web.filter;

/* Filter 与 Interceptor 的区别：
   1、filter基于接口中的doFilter回调函数，Interceptor基于Java本身的反射机制；
   2、Filter依赖于servlet容器，因此仅用于web应用，而Interceptor与servlet无关，即可以在web应用也可以在企业应用；
   3、Filter仅在配置的url或者servlet的请求上启用，而Interceptor针对所有请求都启用；但Filter过滤范围更大，因为可以使用通配符保护页面、图片、文件等，而Interceptor只能过滤请求；
   4、Interceptor只能针对同一个请求的请求和应答进行处理，而Filter可以使用forward在中途变更应答，功能更强；
   5、Filter在web.xml里面配置(SpringMVC的配置)，Interceptor可以在applicationContext.xml里面配置(Spring配置)，实际使用中拦截器可能基于bean的信息进行判断，所以实际配置时还是在SpringMVC配置中；
   6、Interceptor是spring中的一个组件，因此能使用spring的任何资源和对象(通过IOC注入)，Filter属于servlet规范一部分，无该功能；
   7、Filter在只在Servlet前后起作用，而拦截器能够深入到方法前后、异常抛出前后等，因此拦截器的使用具有更大的弹性。在Spring构架的程序中，要优先使用拦截器
 */
/* 所有实现Filter接口的类都要在web.xml中进行配置，默认都是单例模式 */
/* 所有 filterChain.doFilter(request, response) 执行之前的语句都是传给目标servlet之前执行，
                                                    之后的语句都是servlet返回结果之后执行  */
public class UrlFilter implements Filter {
	protected static final Log log = LogFactory.getLog(UrlFilter.class);
	
	public void init(FilterConfig arg0) throws ServletException { } 

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String uri = request.getRequestURI().toLowerCase().trim();
		Cookie[] cookies = request.getCookies();
		boolean cookieFlag = false;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("open")) {
					cookieFlag = true;
					break;
				}
			}
		}
		
		if (!cookieFlag) {
			response.addCookie(new Cookie("open", "true"));
		}

		// 不接受任何jsp请求
		if (uri.endsWith(".jsp")) {
			return;
		}

		// 只拦截.html结尾的请求
		if (!uri.endsWith(".html")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		/*  所有.html结尾的请求，设置其编码格式为 UTF-8  */
		request.setCharacterEncoding("UTF-8");
		filterChain.doFilter(request, response);
	}

	public void destroy()  { }
}
