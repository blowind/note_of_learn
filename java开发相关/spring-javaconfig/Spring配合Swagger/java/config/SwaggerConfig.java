package com.zxf.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @Description: swagger基本配置类
 * @Author: ZhangXuefeng
 * @Date: 2018/11/20 14:13
 * @Version: 1.0
 **/
@Configuration
@EnableSwagger2   /*使swagger2生效*/
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)   /*指定使用swagger 2.0版本*/
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zxf.swagger"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Swagger服务端接口文档测试")
                .description("Swagger功能测试")
                .license("MIT").licenseUrl("http://opensource.org/licenses/MIT")
                .contact(new Contact("zxf", "www.bing.com", "bing@qq.com"))
                .version("1.0.0")
                .build();
        return apiInfo;
    }
}
