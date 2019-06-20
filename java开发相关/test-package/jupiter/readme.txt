JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
其中
1、JUnit Platform是套装的平台框架基础
2、JUnit Jupiter基础测试api集合，提供了TestEngine跑JUnit 5用例
3、JUnit Vintage提供了TestEngine给JUnit 3和4的测试用例


测试类：
所有顶层类，静态成员类，使用@Nested 注解的类，只要这些类包含至少一个测试方法，并且不为abstract类

测试方法：
被以下任何一种注解标记的方法，并且不是abstract方法，没有返回值，不是private
@Test,@RepeatedTest, @ParameterizedTest, @TestFactory, @TestTemplate

生命期方法：
被以下任何一种注解标记的方法，并且不是abstract方法，没有返回值，不是private
@BeforeAll, @AfterAll, @BeforeEach, @AfterEach