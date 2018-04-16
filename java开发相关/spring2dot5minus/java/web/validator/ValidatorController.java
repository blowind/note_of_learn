package web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import web.model.Product;
import web.validator.ProductValidator;

@Controller
public class ValidatorController {

    private static final Log logger = LogFactory
            .getLog(ValidatorController.class);

    @RequestMapping(value = "/add-product")
    public String inputProduct(Model model) {
        model.addAttribute("product", new Product());
        return "ProductForm";
    }

	/*  使用验证器的第一种方法  */
    @RequestMapping(value = "/save-product")
    public String saveProduct(@ModelAttribute Product product, BindingResult bindingResult, Model model) {
		/*  此处要自己初始化并生成验证器对象，因此不需要在xml中配置 */
        ProductValidator productValidator = new ProductValidator();
		/*  此处使用的 bindingResult ，可能在前面已经接受过 converter 或者 formatter 的检验，此处进一步添加 validator 可能检测出来的错误 */
        productValidator.validate(product, bindingResult);

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            logger.debug("Code:" + fieldError.getCode() + ", field:"
                    + fieldError.getField());
            return "ProductForm";
        }

        // save product here

        model.addAttribute("product", product);
        return "ProductDetails";
    }
	
	/*  使用验证器的第二种方法  */
	/*  在 Controller 中编写 initBinder 方法，并将验证器传入到 WebDataBinder ，
	    此后该验证器会应用于该 Controller 所有的请求处理方法  */
	@org.springframework.web.bind.annotation.InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProductValidator());
		binder.validate();
	}
	
	
	/*  使用验证器的第三种方法  */
	/*  使用  javax.validation.Valid  对要验证的对象参数进行注解，Model对象里面需配合进行注解，详见ModelProduct  */
    @RequestMapping(value = "/save-product")
    public String saveProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
            logger.debug("Code:" + fieldError.getCode() + ", object:" + fieldError.getObjectName() + ", field:" + fieldError.getField());
            return "ProductForm";
		}
		//save product here
		model.addAttribute("product", product);
        return "ProductDetails";
	}
	
	
}