package com.springtest;

import com.springtest.service2.SecondService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 测试configuration在测试类中定义的情况，此种情况下不通过location引入模块中的config定义
 * @date 2018/07/13 17:46
 */
@SpringJUnitWebConfig
public class LocalConfigTest {

	@Configuration
	static class Config {
		@Bean
		public SecondService getService() {
			return new SecondService();
		}
	}

	@Autowired
	private SecondService secondService;

	@Test
	public void testName() {
		assertEquals("SecondService", secondService.getName());
	}

}
