package validator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.model.Product;

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
			/*  第一个参数填写异常的字段名，第二个参数填写异常说明  */
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