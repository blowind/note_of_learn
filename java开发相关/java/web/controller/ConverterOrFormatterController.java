package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import web.model.Employee;

@Controller
public class ConverterOrFormatterController {
    
	/*  针对第一次打开时 GET 方法获取的表单待输入页面 */
    @RequestMapping(value="/add-employee")
    public String inputEmployee(Model model) {
        model.addAttribute(new Employee());
        return "EmployeeForm";
    }

	/* 针对输入完毕后 POST 方法提交的表单提交页面 */
    @RequestMapping(value="/save-employee")
    public String saveEmployee(@ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
		/* 此处使用 bindingResult 获取 @ModelAttribute 通过 converter 或者 formatter 转换过程中产生的转换 error ，这部分error已经放入model */
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return "EmployeeForm";
        }
        // save employee here
        
        model.addAttribute("employee", employee);
        return "EmployeeDetails";
    }
}