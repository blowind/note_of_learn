package com.springtest;

import com.springtest.config.MvcConfig;
import com.springtest.service.ActiveService;
import com.springtest.service.BaseService;
import com.springtest.service.SilentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description spring结合jupiter的测试
 * @date 2018/07/13 13:10
 */

/** 如果SpringJupiterTest extends了一个父类，
 *  则通过inheritLocations和inheritInitializers选择是否继承父类的资源和初始化器
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MvcConfig.class, inheritLocations = true, inheritInitializers = true)
@WebAppConfiguration
@ActiveProfiles("dev")
public class SpringJupiterTest {

	@Autowired
	BaseService baseService;

	@Autowired
	ActiveService activeService;

	@Autowired
	WebApplicationContext wac;

	@Test
	void testOK() {
		String result = baseService.returnOK();
		assertEquals("BaseService", result);
	}

	@Test
	void testActiveServiceIsNotNull() {
		assertNotNull(activeService);
	}

	@Test
	void testSilentServiceNotInitialized() {
		Throwable exception = assertThrows(NoSuchBeanDefinitionException.class,
				() -> { SilentService silentService = wac.getBean(SilentService.class);});
	}

}
