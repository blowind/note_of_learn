package com.simple.jupiter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: Test10Parameterized
 * @Description: 根据传入参数进行测试，
 *                 此处参数都是定制化指定的，需要依赖junit-jupiter-params包
 *                 本API当前处于试验状态
 * @Author: ZhangXuefeng
 * @Date: 2019/6/22 17:10
 * @Version: 1.0
 **/
public class Test10Parameterized {

    /**
     * @ValueSource 最简单的参数指定注解
     * 指定一个字面量的数组，数组的每个元素就是每次执行的入参
     * 可用类型： short, byte, int, long, float, double, char
     *            java.lang.String, java.lang.Class
     */
    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void palindromes(String candidate) {
        assertTrue(StringUtils.isNotBlank(candidate));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testWithValueSource(int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    /**
     * 测试入参是null的边界场景
     * @NullSource 注解的方法入参必须为非基本类型
     */
    @ParameterizedTest
    @NullSource
    void testWithNullSource(String a) {
        assertNull(a, "空入参");
    }

    /**
     * 测试入参集合是空的场景
     * @EmptySource 用于入参类型是集合的情况
     * 入参类型一般是： String, List, Set, Map,
     *                  基本类型数组，对象数组
     */
    @ParameterizedTest
    @EmptySource
    void testEmptySource(List<Integer> ints) {
        assertEquals(0, ints.size(), "集合大小是0");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullAndEmptySource(String a) {
        assertTrue(a == null || a.trim().isEmpty());
    }

    /**
     * 入参是null或者空字符串的所有场景
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void nullEmptyAndBlankStrings(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    /**
     * 同上，简化写法
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    void nullEmptyAndBlankStrings2(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    /**
     * @EnumSource 使用枚举类型作为入参进行测试
     */
    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    void testEnumSource(TimeUnit timeUnit) {
        assertNotNull(timeUnit);
    }

    /**
     * 指定具体用做参数的枚举值
     */
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, names = { "DAYS", "HOURS"})
    void testEnumSourceInclude(TimeUnit timeUnit) {
        assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
    }

    /**
     * 使用排除模式指定枚举入参范围
     * 此处由jupiter自带的隐式类型转换功能
     * 如将DAYS、HOURS字符串转为入参的TimeUnit类型
     */
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.EXCLUDE, names = {"DAYS", "HOURS"})
    void testEnumSourceExclude(TimeUnit timeUnit) {
        assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
        assertTrue(timeUnit.name().length() > 5);
    }

    /**
     * 使用正则表达式匹配过滤符合要求的入参
     * 主要必须至少有一个入参符合过滤要求，否则报错
     */
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.MATCH_ALL, names = "^(M|N).+SECONDS$")
    void testEnumSourceRegex(TimeUnit timeUnit) {
        String name = timeUnit.name();
        assertTrue(name.startsWith("M") || name.startsWith("N"));
        assertTrue(name.endsWith("SECONDS"));
    }

    /**
    @MethodSource 只能用于外部类/测试类的静态工厂方法，
    并且这些工厂方法不能有入参，返回值要是Stream或者可以转为Stream的类型
    除非测试类被@TestInstance(Lifecycle.PER_CLASS)注解
    */

    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }

    /**
     * 指定工厂方法作为参数来源
     * @MethodSource 不指定值时会查找函数名同名静态工厂方法
     */
    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static IntStream range() {
        return IntStream.range(0, 20).skip(10);
    }

    /**
     * 测试返回IntStream的静态工厂方法
     */
    @ParameterizedTest
    @MethodSource("range")
    void testWithRangeMethodSource(int argument) {
        assertNotEquals(9, argument);
    }

    /**
     * 引入外部类的静态工厂方法作为参数来源，注意要全限定名
     */
    @ParameterizedTest
    @MethodSource("com.simple.jupiter.Test10StringProviders#tinyStrings")
    void testWithExternalMethodSource(String tinyString) {
        System.out.println(tinyString);
        assertTrue(tinyString.length() < 4);
    }

    /**
     * 使用Arguments聚合多个入参传入的场景
     */
    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                Arguments.arguments("apple", 1, Arrays.asList("a", "b")),
                Arguments.arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >= 1 && num <= 2);
        assertEquals(2, list.size());
    }

    /**
     * 使用@ArgumentsSource引入ArgumentsProvider实现类作为参数流的场景
     * 注意ArgumentsProvider实现类必须是独立的类或者static内部类
     */
    static class MyArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("apple", "banana").map(Arguments::of);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentProvider.class)
    void testWithArgumentsSource(String argument) {
        assertNotNull(argument);
    }

    /**
     * 使用@CsvSource 注入,分隔的参数列表，
     * 其中字符串可以用''表示，空出不填的地方表示null
     * 基本类型必须填，不能为null
     */
    @ParameterizedTest
    @CsvSource({
            "apple, 1",
            "banana, 2",
            "'lemon, line', 0xF1"
    })
    void testWtihCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    /**
     * @CsvFileSource 引入外部csv文件的内容作为参数来源
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0,  reference);
    }

    /**
     * 参数的隐式类型转换规则：
    1、参数为基本类型的，按照对应类型转换规则从字符串转换入参类型；
    2、常用类型隐式转换，根据参数类型转换，
       诸如上面的字符串转枚举，传入路径String根据入参File类型转为文件等
       参见jupiter官方文档
    3、回调式的字符串转对象，参数类有如下特性：
        1) 有静态非私有工厂方法，仅有一个String类型入参，返回入参类型；
        2) 入参类型是非内部类或者静态的内部类，有一个非私有的构造函数且构造函数仅有一个String类型的入参；
       当符合上面条件的函数存在一个时作为类型转换函数调用，存在多个时都被忽视，
       两个条件都符合的函数各存在一个，则调用1)函数被调用
     */

    @ParameterizedTest
    @ValueSource(strings = {"01.01.2019", "31.12.2019"})
    void testWithExplicitJavaTimeConverter(@JavaTimeConversionPattern("dd.MM.yyyy") LocalDate argument){
        assertEquals(2019, argument.getYear());
    }

    /**
     * 使用@ConvertWith和自定义转换类指定测试函数入参的类型转换
     */
    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    void testWithExplicitArgumentConvertion(@ConvertWith(Test10ToStringArgumentConverter.class) String argument) {
        assertNotNull(TimeUnit.valueOf(argument));
    }



}
