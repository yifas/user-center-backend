package com.yupi.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile("dev") //控制仅在开发环境下使用接口文档
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
       return new Docket(DocumentationType.SWAGGER_2)
               //apiInfo() 抽取成方法 方便读配置
                .apiInfo(apiInfo())
                //分组名称
                //.groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.yupi.usercenter.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("用户中心")
                .description("用户中心接口文档")
//                .termsOfServiceUrl("http://www.xx.com/")
                .contact(new Contact("chen","http://github.com","abc"))
                .version("1.0")
                .build();
    }
}
