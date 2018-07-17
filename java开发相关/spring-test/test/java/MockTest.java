package com.springtest;

import com.springtest.config.MvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description 结合mock对象测试
 * @date 2018/07/17 12:51
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
public class MockTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	//  设置默认路径和上下文，暂时没搞清用法
//	@BeforeEach
//	void setup(WebApplicationContext wac) {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).defaultRequest(get("/").servletPath("/accounts/1")).build();
//	}


	@Test
	void testGetAccountByGet() throws Exception {
		ResultActions ret = this.mockMvc.perform(get("/accounts/1")
						     .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
		ret.andExpect(status().is(200))
		   .andExpect(content().contentType("application/json;charset=UTF-8"))
		   .andExpect(jsonPath("$.code").value(0))
		   .andExpect(jsonPath("$.msg").value("is one"))
		   .andExpect(jsonPath("$.data.moneyType").value("BTC"));
	}

	@Test
	void testGetAccountNotExist() throws Exception {
		this.mockMvc.perform((get("/accounts/100").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))))
				    .andExpect(status().is(200))
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$.code").value(101))
					.andExpect(jsonPath("$.msg").value("not found"));

	}

	@Test
	void testGetAccountByPost() throws Exception {
		this.mockMvc.perform(post("/accounts/{id}/details", 1).accept(MediaType.APPLICATION_JSON))
				    .andExpect(status().is(200))
				    .andExpect(jsonPath("$.data.precise").value(0.001));
	}

	@Test
	void testMultipartInterface() throws Exception {
		// 注意此处file的第一个参数内容varName必须与请求的接口中变量名相同，否则上传失败
		this.mockMvc.perform(multipart("/accounts/doc").file("varName", "ABC".getBytes("UTF-8"))
				                                                  .param("tag", "3")
		                                                          .accept(MediaType.ALL))
				    .andExpect(status().is(200))
				    .andExpect(jsonPath("$.code").value(0))
					.andExpect(jsonPath("$.data.size").value("ABC".length()));
	}


	@Test
	void testMultipartInterface2() throws Exception {
		String tagName = "data";
		String originFilename = "filename.txt";
		String content = "some xml";

		// 第一个参数内容必须与请求的接口变量名相同，否则服务端无法映射到变量
		MockMultipartFile file1 = new MockMultipartFile(tagName, originFilename, "text/plain", content.getBytes());
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/accounts/upload").file(file1).param("random", "4"))
				    .andExpect(status().is(200))
					.andDo(print())       //  打印整个请求的数据
					.andExpect(jsonPath("$.code").value(0))
					.andExpect(jsonPath("$.data.tagName").value(tagName))
					.andExpect(jsonPath("$.data.originFilename").value(originFilename))
					.andExpect(jsonPath("$.data.size").value(content.length()))
					.andExpect(jsonPath("$.data.content").value(content));
	}

}
