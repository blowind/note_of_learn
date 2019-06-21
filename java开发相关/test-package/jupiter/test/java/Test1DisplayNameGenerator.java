package com.simple.jupiter;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;

/**
 * @ClassName: Test1DisplayNameGenerator
 * @Description: 定制测试类/测试方法的展示名称
 * @Author: apple
 * @Date: 2019/6/21 上午10:14
 * @Version: 1.0
 **/
public class Test1DisplayNameGenerator {

    /**
     * 使用jupiter工具类DisplayNameGenerator自带的用例名格式化类ReplaceUnderscores来定制
     */
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class A_year_is_not_supported {

        /**
         *  使用空格替换掉函数名种的_，用做用例显示的名字
         */
        @Test
        void if_it_is_zero() {}

        /**
         * 针对每个参数跑一次测试用例，
         * 使用ParameterizedTest中的name加上参数作为每次测试的名称
         */
        @DisplayName("测试负值用例")
        @ParameterizedTest(name = "用例: 不支持年份: {0}")
        @ValueSource(ints = { -1, -4 })
        void if_it_is_negative(int year) {}
    }

    /**
     * 使用继承ReplaceUnderscores的定制类IndicativeSentences来生成用例名
     */
    @Nested
    @DisplayNameGeneration(IndicativeSentences.class)
    class A_year_is_a_leap_year {

        /**
         * 将所有下划线替换为空格作为用例名展示
         */
        @Test
        void if_it_is_divisible_by_4_but_not_by_100() {}

        /**
         * /**
         * 将所有下划线替换为空格作为用例集群名展示
         * 在集群内部，使用代入参数的name作为用例名展示
         */
        @ParameterizedTest(name = "年份 {0} 是个闰年")
        @ValueSource(ints = { 2016, 2020, 2048 })
        void if_it_is_one_of_the_following_years(int year) {}
    }

    static class IndicativeSentences extends DisplayNameGenerator.ReplaceUnderscores {

        /**
         *  最外层封装类的名称修改
         */
        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return super.generateDisplayNameForClass(testClass) + "[最外层的类]";
        }

        /**
         * 修改@Nested注解的类的用例展示名
         */
        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return super.generateDisplayNameForNestedClass(nestedClass) + "[内部封装类]";
        }

        /**
         * 修改每个注解方法的展示名
         */
        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            String name = testClass.getSimpleName() + " " + testMethod.getName();
            return name.replace('_', ' ') + "[测试函数]";
        }
    }
}
