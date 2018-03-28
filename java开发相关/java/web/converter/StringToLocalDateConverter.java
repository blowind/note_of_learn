package web.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/* 此处泛型分别制定入参类型和返回值类型，主要是接口中定义的 convert方法中的相关类型，
   所有Controller中路径映射方法涉及的 ModelAttribute 注解对象中属性涉及到此种类型转换的地方都需要经过本过滤器处理
   此处显示的是入参从String转为LocalDate时，会通过本bean进行格式转换，
   Converter接口不限定入参类型，Formatter限定为String*/
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private String datePattern;

    public StringToLocalDateConverter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public LocalDate convert(String s) {
        try{
            return LocalDate.parse(s, DateTimeFormatter.ofPattern(datePattern));
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, only " + datePattern + "is support\n");
        }
    }
}
