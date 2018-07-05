package com.junit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("所有断言方法举例")
public class AssertionsDemo {

    static Person person;
    @BeforeAll
    static void init() {
        person = new Person("John", "Doe");
    }

    @Test
    void standardAssertions() {
        assertEquals(2, 2);
        assertEquals(4, 4, "显示额外测试说明");
        assertTrue('a' < 'b', () -> "额外说明信息可以延迟计算");
    }

    @Test
    void groupedAssertion() {
        /* 集群断言中，所有断言都被执行，其中的失败项会一起报告 */
        assertAll("person",
                () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName()));
    }

    @Test
    void dependentAssertions() {
        // 代码块中一个测试失败后，块中后序代码被跳过
        assertAll("properties",
                () -> {
                    String firstName = person.getFirstName();
                    assertNotNull(firstName);
                    // 若上述assert失败，此处assertAll被跳过
                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("n"))  );
                    },
                () -> {
                    //  此处属于不同块，因此上面的失败不影响此处执行
                    String lastName = person.getLastName();
                    assertNotNull(lastName);

                    assertAll("lastName",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e"))  );
                    }
        );
    }

    @Test
    void exceptionTesting() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        assertTimeout(Duration.ofMinutes(2), () -> {
            System.out.println("in 2 mins");
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(Duration.ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded() {
        // 此处测试失败，因为休眠时间超过了10秒最大限制，但会等100毫秒休眠执行完毕
        assertTimeout(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        // 抢占式中断，测试失败，但是不会等到休眠结束
        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        });
    }

}
