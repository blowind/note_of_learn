package com.springtest;

import com.springtest.controller.AccountController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 不通过spring上下文来生成mockMvc，而是直接指定需要的controller
 * @date 2018/07/17 14:02
 */
public class MockNoContextTest {

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController()).build();
	}

	@Test
	void testGetAccount() throws Exception {
		ResultActions ret = this.mockMvc.perform(get("/accounts/1")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		ret.andExpect(status().is(200))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.code").value(0))
				.andExpect(jsonPath("$.msg").value("is one"))
				.andExpect(jsonPath("$.data.moneyType").value("BTC"));
	}
}
