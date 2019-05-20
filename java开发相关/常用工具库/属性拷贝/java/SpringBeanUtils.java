package com.zxf;

import com.zxf.pojo.NewPOJO;
import com.zxf.pojo.OldPOJO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpringBeanUtils {

    public static <T, E> E clone(T source, Class<E> classType) {

        if (source == null) {
            return null;
        }
        E targetInstance = null;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }


    public static <T, E> List<E> batchClone(List<T> sourceList, Class<E> classType) {
        if (sourceList == null) {
            return null;
        }
        List<E> result = new ArrayList<E>();
        int size = sourceList.size();
        for (int i = 0; i < size; i++) {
            result.add(clone(sourceList.get(i), classType));
        }
        return result;
    }

    public static void main(String[] args) {
        OldPOJO one = new OldPOJO();
        one.setMyString("String one");
        one.setMyDate(new Date());
        one.setMyInt(1);
        one.setMyFloat(2.2f);
        one.setDoubleOfOld(3.33);

        NewPOJO newOne = SpringBeanUtils.clone(one, NewPOJO.class);
        System.out.println(newOne);
    }

}
