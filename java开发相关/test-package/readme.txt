基本测试框架：
1、Junit Jupiter(Junit 5)
2、Juint 4
3、TestNG


第三方断言框架(库):
1、AssertJ
2、Hamcrest
3、Truth



暂时留存区：


在spring测试中设置环境变量或系统属性，可以在静态初始化器中初始化System属性，如下：

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:whereever/context.xml")
public class TestWarSpringContext {

    static {
        System.setProperty("myproperty", "foo");
    }

}