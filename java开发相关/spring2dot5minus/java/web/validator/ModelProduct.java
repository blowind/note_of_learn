package domain;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ModelProduct {
    private static final long serialVersionUID = 78L;

    /* 该属性值必须在指定范围内 */
    @Size(min = 1, max = 10)
    private String name;

    /* 该属性必须为假 */
    @AssertFalse
    boolean hasChildren;

    /* 该属性必须为真 */
    @AssertTrue
    boolean isEmpty;

    /* 该属性必须小于或等于指定值的小数 */
    @DecimalMax("1.1")
    BigDecimal price;

    /* 该属性必须大于或等于指定值的小数 */
    @DecimalMin(("0.04"))
    BigDecimal buyPrice;

    /* 该属性必须在指定范围内，integer指定数值的最大整数部分，fraction属性定义该数值的最大小数部分  */
    @Digits(integer = 5, fraction = 2)
    BigDecimal sellPrice;

    /* 该属性必须是未来的一个日期 */
    @Future
    Date shippingDate;

    /* 该属性必须是一个过去的日期 */
    @Past
    private LocalDate productionDate;

    /* 该属性必须小于或等于指定值的整数 */
    @Max(150)
    int age;

    /* 该属性必须大于或等于指定值的整数 */
    @Min(18)
    int adultAge;

    /* 该属性不能为Null */
    @NotNull
    String firstName;

    /* 该属性必须是Null */
    @Null
    String testString;

    /* 该属性必须与指定的常规表达式相匹配 */
    @Pattern(regext="\\d{3}")
    String areaCode;
}
