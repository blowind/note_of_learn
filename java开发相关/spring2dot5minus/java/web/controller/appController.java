

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value = "/article")  /*  指定本类映射的路径，从根路径开始算 */
public class ArticleController extends PaginationController {
    @Autowired
    private ArticleBO articleBO;
	
    /***********************  	@RequestMapping方法方法所支持的常见参数类型：     ***********************/
/***
***  1、请求或响应对象（Servlet API）。可以是任何具体的请求或响应类型的对象，比如，ServletRequest或HttpServletRequest对象。
***  2、HttpSession类型的会话对象（Servlet API）。使用该类型的参数将要求这样一个session的存在，因此这样的参数永不为null。
***  3、当前请求的地区信息java.util.Locale，由已配置的最相关的地区解析器解析得到。
		在MVC的环境下，就是应用中配置的LocaleResolver或LocaleContextResolver
***  4、与当前请求绑定的时区信息java.util.TimeZone（java 6以上的版本）/java.time.ZoneId（java 8），
		由LocaleContextResolver解析得到 
***  5、org.springframework.http.HttpMethod。可以拿到HTTP请求方法
***  6、包装了当前被认证用户信息的java.security.Principal
***  7、带@PathVariable标注的方法参数，其存放了URI模板变量中的值。
***  8、带@RequestParam标注的方法参数，其存放了Servlet请求中所指定的参数。参数的值会被转换成方法参数所声明的类型。
***  9、带@RequestHeader标注的方法参数，其存放了Servlet请求中所指定的HTTP请求头的值。参数的值会被转换成方法参数所声明的类型。
***  10、带@RequestBody标注的参数，提供了对HTTP请求体的存取。参数的值通过HttpMessageConverter被转换成方法参数所声明的类型。
***  11、带@RequestPart标注的参数，提供了对一个"multipart/form-data请求块（request part）内容的存取。
***  12、HttpEntity<?>类型的参数，其提供了对HTTP请求头和请求内容的存取。
		 请求流是通过HttpMessageConverter被转换成entity对象的。
***  13、java.util.Map/org.springframework.io.Model/org.springframework.ui.ModelMap类型的参数，
         用以增强默认暴露给视图层的模型(model)的功能
***  14、org.springframework.web.servlet.mvc.support.RedirectAttributes类型的参数，
		 用以指定重定向下要使用到的属性集以及添加flash属性（暂存在服务端的属性，它们会在下次重定向请求的范围中有效）。
***  15、命令或表单对象，它们用于将请求参数直接绑定到bean字段（可能是通过setter方法）。
		 你可以通过@InitBinder标注和/或HanderAdapter的配置来定制这个过程的类型转换。
		 RequestMappingHandlerAdapter类webBindingInitializer属性的文档。
		 这样的命令对象，以及其上的验证结果，默认会被添加到模型model中，键名默认是该命令对象类的类名——
		 比如，some.package.OrderAddress类型的命令对象就使用属性名orderAddress类获取。
		 ModelAttribute标注可以应用在方法参数上，用以指定该模型所用的属性名
***  16、org.springframework.validation.Errors / org.springframework.validation.BindingResult验证结果对象，
		 用于存储前面的命令或表单对象的验证结果（紧接其前的第一个方法参数）。
***  17、org.springframework.web.bind.support.SessionStatus对象，用以标记当前的表单处理已结束。
		 这将触发一些清理操作：@SessionAttributes在类级别标注的属性将被移除
***  18、org.springframework.web.util.UriComponentsBuilder构造器对象，用于构造当前请求URL相关的信息，
		 比如主机名、端口号、资源类型（scheme）、上下文路径、servlet映射中的相对部分（literal part）等

***/

     	/***********************  	@RequestMapping方法方法支持的常见返回类型：     ***********************/
/***
***  1、ModelAndView对象，其中model隐含填充了命令对象，以及标注了@ModelAttribute字段的存取器被调用所返回的值。
***  2、Model对象，其中视图名称默认由RequestToViewNameTranslator决定，
		model隐含填充了命令对象以及标注了@ModelAttribute字段的存取器被调用所返回的值
***  3、Map对象，用于暴露model，其中视图名称默认由RequestToViewNameTranslator决定，
		model隐含填充了命令对象以及标注了@ModelAttribute字段的存取器被调用所返回的值
***  4、View对象。其中model隐含填充了命令对象，以及标注了@ModelAttribute字段的存取器被调用所返回的值。
		handler方法也可以增加一个Model类型的方法参数来增强model
***  5、String对象，其值会被解析成一个逻辑视图名。
		其中，model将默认填充了命令对象以及标注了@ModelAttribute字段的存取器被调用所返回的值。
		handler方法也可以增加一个Model类型的方法参数来增强model
***  6、void。如果处理器方法中已经对response响应数据进行了处理
		（比如在方法参数中定义一个ServletResponse或HttpServletResponse类型的参数并直接向其响应体中写东西），
		那么方法可以返回void。handler方法也可以增加一个Model类型的方法参数来增强model
***  7、如果处理器方法标注了ResponseBody，那么返回类型将被写到HTTP的响应体中，
		而返回值会被HttpMessageConverters转换成所方法声明的参数类型。
***  8、HttpEntity<?>或ResponseEntity<?>对象，用于提供对Servlet HTTP响应头和响应内容的存取。
		对象体会被HttpMessageConverters转换成响应流。
***  9、HttpHeaders对象，返回一个不含响应体的response
***  10、如果返回类型不是Spring MVC默认识别的类型，则会被处理成model的一个属性并返回给视图，
		该属性的名称为方法级的@ModelAttribute所标注的字段名（或者以返回类型的类名作为默认的属性名）。
		model隐含填充了命令对象以及标注了@ModelAttribute字段的存取器被调用所返回的值
***/
	
	/*
		value:  表示需要匹配的url的格式。
		method: 表示所需处理请求的http 协议(如get,post,put,delete等)，可选值为RequestMethod这个enum的值。
		params: 格式为”paramname=paramvalue” 或 “paramname!=paramvalue”。不带参数则表示paramvalue可以为任意值。
				如params =  {"param1=1","param2!=2","param3"},表示对应的url必须包括param1,param2,param3三个参数，
				其中param1的值必须为1，param2的值不能为2，param3的值可以为任意值。
		headers:用来限定对应的reqeust请求的headers中必须包括的内容，例如 headers={"Connection=keep-alive"}, 
				表示请求头中的connection的值必须为keep-alive。
	*/
	/* 指定本方法映射的路径及HTTP请求方法，可以用,分隔的大括号指定多个HTTP请求方法，仅一个HTTP方法时无需花括号  */
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})    
    public String index(@ModelAttribute("q") Article q, @RequestParam(defaultValue = "1") int pageNumber, Model model) {
        int totalNumberOfElements = articleBO.count(q);
        List<Article> elements = articleBO.find(q, ((new OrderBy()).add("id")), Constants.DEFAULT_PAGE_SIZE, pageNumber);

        List<ArticleCategory> articleCategoryList = articleCategoryBO.find();
        Map<String, ArticleCategory> articleCategoryMap = new HashMap<>();
        for(ArticleCategory ac : articleCategoryList) {
            articleCategoryMap.put(ac.getId().toString(), ac);
        }

        model.addAttribute("acm", articleCategoryMap);
        model.addAttribute("p", PaginationUtils.newPagination(Constants.DEFAULT_PAGE_SIZE, pageNumber, totalNumberOfElements, elements));

        return "/article/index";
    }
	
	/*  @RequestMapping 参数中的url，除了常规的url外，也可以使用url template来定义形成类似REST请求的url  */
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	/*  注意使用路径变量功能时，形参中的变量名id必须与url中花括号的变量名id一致，类型可以不是字符串 */
    public String update(Article article, @PathVariable int id, @RequestParam("picFile") MultipartFile picFile, String redirectUrl, RedirectAttributes redirectAttributes) {
        Map<String, String> result = UpYunUtils.upload(picFile, "/article");
        if ("000".equals(result.get("code"))) {
            article.setPicPath(result.get("url"));
            String picWidth = result.get("picWidth");
            String picHeight = result.get("picHeight");
            article.setPicWidth(picWidth != null ? Integer.valueOf(picWidth) : null);
            article.setPicHeight(picHeight != null ? Integer.valueOf(picHeight) : null);
        }

        if (article.getIsLink() == 0) {
            article.setLinkUrl(null, true);
        } else {
            article.setSummary(null, true);
            article.setContent(null, true);
        }

        articleBO.update(article, id);
		
		/* 通过flash属性给重定向的页面带信息，一般的 model.addAttribute 方法并不能给重定向的网页带信息，而forward转发信息可以 */
		redirectAttributes.addFlashAttribute("message", "There are some info to pass")

		/*  注意转发和重定向的区别：
			转发比重定向快，因为重定向会先返回给浏览器让浏览器重新发送请求，但重定向有以下好处：
			1、可以重定下到外部网站；
			2、重定向后的网页网址变成新的网址(此处为/detail)，这样不会因为刷新导致数据重复提交；
		*/
        return "redirect:/detail/" + id;
    }
	
	@RequestMapping(value = "/detail/{id}")
	public String viewDetail(@PathVariable Long id, Model model) {
		/* 获取之前必然有add或者update参数在后端插入文章 */
		Article article = articleBO.get(id);
		model.addAttribute("article", article);
		/* 对应有一个 ArticleDetail.jsp 或者 ArticleDetail.ftl 的页面文件用于显示 */
		return "ArticleDetail";
		
	}
}