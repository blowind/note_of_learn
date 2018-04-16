package validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.model.Product;

/*  验证器必须配合Controller控制器和其中的参数BindingResult进行使用，具体内容参看ValidatorController实现
    其中最关键的validate方法返回一个包含错误信息的字符串列表，若返回一个空列表，则表示输入合法 */
/*  对于新项目，推荐使用JSR 303 的验证器而不是此处Spring框架的Validation验证器  */
public class ProductValidator implements Validator {

	/*   设置需要验证的类类型，对于需要验证的类返回true  */
    @Override
    public boolean supports(Class<?> klass) {
        return Product.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
	/*  使用工具类 ValidationUtils 辅助验证，减少重复代码 ，参数2是属性名，参数3是引用的 messageResource 文件中的字段key */
        ValidationUtils.rejectIfEmpty(errors, "name", "productName.required");
        ValidationUtils.rejectIfEmpty(errors, "price", "price.required");
        ValidationUtils.rejectIfEmpty(errors, "productionDate", "productionDate.required");
        
        BigDecimal price = product.getPrice();
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
			/*  第一个参数填写异常的属性名，第二个参数填写message.properties文件中错误信息键值对的键  */
            errors.rejectValue("price", "price.negative");
        }
        LocalDate productionDate = product.getProductionDate();
        if (productionDate != null) {
            if (productionDate.isAfter(LocalDate.now())) {
                errors.rejectValue("productionDate", "productionDate.invalid");
            }
        }
    }
}