package com.zxf;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/* 声明一个控制器建言，组合了@Component注解，不限定时该Bean里面的方法对所有@Controller生效 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

	/* 此处定义全局处理，value指定过滤拦截的条件，此处拦截所有Exception */
	@ExceptionHandler(value = Exception.class)
	public ModelAndView exception(Exception exception, WebRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("errorMessage", exception.getMessage());
		return modelAndView;
	}

	/* 本注解将键值对添加到全局，即所有注解@RequestMapping的方法都获得此键值对 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("msg", "额外信息");
	}

	/* 定制WebDataBinder，此处忽略request参数的id */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}


}
