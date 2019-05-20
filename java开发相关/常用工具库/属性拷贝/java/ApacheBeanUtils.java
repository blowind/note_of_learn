package com.zxf;

import com.zxf.pojo.NewPOJO;
import com.zxf.pojo.OldPOJO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 不建议使用commons-beanutils包中的BeanUtils类，原因如下：
 * 1、对于内部静态类的对象复制也会出现问题，检验太复杂了
 * 2、采用反射机制实现的，对程序的效率也会有影响
 */
public class ApacheBeanUtils {

    public static <T, E> E clone(T source, Class<E> classType) {
        if (source == null) {
            return null;
        }

        E dest = null;
        try{
            dest = classType.newInstance();
            BeanUtils.copyProperties(dest, source);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        }
        return dest;
    }

    /*
     * 与BeanUtils的同名方法功能相似，但提供类型转换功能
     * 即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换
     */
    public static <T, E> E cloneAndConvert(T source, Class<E> classType) {
        if (source == null) {
            return null;
        }

        E dest = null;
        try{
            dest = classType.newInstance();
            PropertyUtils.copyProperties(dest, source);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException ite) {
            throw new RuntimeException(ite);
        } catch (NoSuchMethodException nme) {
            throw new RuntimeException(nme);
        }
        return dest;
    }

    public static void main(String[] args) {
        OldPOJO one = new OldPOJO();
        one.setMyString("String one");
        one.setMyDate(new Date());
        one.setMyInt(1);
        one.setMyFloat(2.2f);
        one.setDoubleOfOld(3.33);

        NewPOJO newOne = ApacheBeanUtils.clone(one, NewPOJO.class);
        System.out.println(newOne);


        NewPOJO newOne2 = ApacheBeanUtils.cloneAndConvert(one, NewPOJO.class);
        System.out.println(newOne2);

    }
}
