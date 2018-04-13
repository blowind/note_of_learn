public class TimeBasedAccessInterceptor extends HandlerInterceptorAdapter {    
    private int openingTime;    
    private int closingTime;    
    private String mappingURL;//利用正则映射到需要拦截的路径    
	
    public void setOpeningTime(int openingTime) {    
        this.openingTime = openingTime;    
    }    
    public void setClosingTime(int closingTime) {    
        this.closingTime = closingTime;    
    }    
    public void setMappingURL(String mappingURL) {    
        this.mappingURL = mappingURL;    
    }    
	
	/* 请求下发到 controller 之前执行，可以进行编码、安全控制等处理  */
    @Override    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {    
        String url=request.getRequestURL().toString();    
        if(mappingURL==null || url.matches(mappingURL)){    
            Calendar c=Calendar.getInstance();    
            c.setTime(new Date());    
            int now=c.get(Calendar.HOUR_OF_DAY);    
            if(now<openingTime || now>closingTime){    
                request.setAttribute("msg", "注册开放时间：9：00-12：00");    
                request.getRequestDispatcher("/msg.jsp").forward(request, response);    
                return false;    
            }    
            return true;    
        }    
        return true;    
    }

	/* controller 处理完毕后，视图引擎渲染之前执行，有机会修改ModelAndView */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	
	/* 视图引擎渲染之后，浏览器收到应答之前执行，可以根据ex是否为null判断是否发生了异常，进行日志记录 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
} 