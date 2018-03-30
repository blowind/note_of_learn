package web.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/* �˴����ͷֱ��ƶ�������ͺͷ���ֵ���ͣ���Ҫ�ǽӿ��ж���� convert�����е�������ͣ�
   ����Controller��·��ӳ�䷽���漰�� ModelAttribute ע������������漰����������ת���ĵط�����Ҫ����������������
   �˴���ʾ������δ�StringתΪLocalDateʱ����ͨ����bean���и�ʽת����
   Converter�ӿڲ��޶�������ͣ�Formatter�޶�ΪString*/
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
