package com.simple.jupiter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName: Test2JunitAssertions
 * @Description: 所有断言方法举例
 * @Author: zhangxuefeng
 * @Date: 2019/6/21 上午10:27
 * @Version: 1.0
 **/

@DisplayName("所有断言方法举例")
public class Test2JunitAssertions {

    static Person person;
    @BeforeAll
    static void init() {
        person = new Person("John", "Doe");
    }

    /**
     * 基本的测试，断言相等、返回true，判空等
     */
    @Test
    void standardAssertions() {
        assertEquals(2, 2);
        assertEquals(4, 4, "失败时显示关于本测试用例的说明");
        assertNotEquals(3, 9);
        assertTrue('a' < 'b', () -> "使用lambda表达式显示失败说明，延迟执行");
        assertFalse(3 < 2);
        assertNull(null);
        assertNotNull(person);
    }

    /**
     *  集群（分组）断言，其中作为参数的每个断言都被执行，其中的失败项会一起报告
     *  适用于测试一个类的各个功能函数或者相同业务的不同处理方法
     */
    @Test
    void groupedAssertion() {
        // 参数一是集群的名称，后面每一个参数是lambda表达式形式的断言
        assertAll("person",
                () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName()));
    }

    /**
     * 集群断言中，每个参数的内部某行执行失败，会阻塞后续的执行，断言之间是相互独立的
     */
    @Test
    void dependentAssertions() {
        // 代码块中一个测试失败后，块中后序代码被跳过
        assertAll("properties",
                () -> {
                    String firstName = person.getFirstName();
                    assertNotNull(firstName);
                    // 若上述assert失败，此处assertAll被跳过，不执行
                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("n"))  );
                },
                () -> {
                    //  此处属于不同块，因此上面的失败不影响此处执行
                    String lastName = person.getLastName();
                    assertNotNull(lastName);

                    System.out.println("查看此处是否继续执行");

                    assertAll("lastName",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e"))  );
                }
        );
    }

    /**
     * 异常断言，用于测试用例抛出异常的情况
     */
    @Test
    void exceptionTesting() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }

    /**
     * 超时断言，测试用例执行时间不超过某个上限
     */
    @Test
    void timeoutNotExceeded() {
        assertTimeout(Duration.ofMinutes(2), () -> {
            System.out.println("in 2 mins");
        });
    }

    /**
     * 超时断言，测试用例执行时间不超过某个上限，并且用例有返回值且返回值符合预期
     */
    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(Duration.ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    private static String greeting() {
        return "Hello, World";
    }

    /**
     * 超时断言，测试用例执行时间不超过某个上限，并且方法引用返回的结果符合预期
     */
    @Test
    void timeoutNotExceededWithMethod() {
        String actualGreeting = assertTimeout(Duration.ofMinutes(2), Test2JunitAssertions::greeting);
        assertEquals("Hello, World", actualGreeting);
    }


    /**
     * 超时断言，测试超时的场景
     * 此处休眠时间超过了10秒最大限制，但会等100毫秒休眠执行完毕
     * 提示信息如下： execution exceeded timeout of 10 ms by 94 ms
     */
    @Test
    void timeoutExceeded() {
        assertTimeout(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        }, "执行时间大于预期的10毫秒");
    }

    /**
     * 超时断言，测试超时的场景
     * 抢占式中断，超时后测试失败立即返回，不会等到休眠结束（程序实际执行完毕）
     * 提示信息如下：execution timed out after 10 ms
     * 不代表本用例执行时间一定比timeoutExceeded()快，特别是用例耗时很短时
     *
     * 注意：
     * 此处抢占式是通过另开线程执行实现的，
     * 所以对于Spring事务这种依赖本线程ThreadLocal变量实现回滚的方案会有副作用（执行失败后不会滚）
     */
    @Test
    void timeoutExceededWithPreemptiveTermination() {
        //
        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        }, "执行超时，立即返回");
    }
}
