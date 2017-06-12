package com.biostime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.and;

/**
 * Created by 13006 on 2017/6/10.
 * 访问路径：ip:端口/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .groupName("妈妈100-act")
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(and(RequestHandlerSelectors.basePackage("com.biostime.controller"),
                        RequestHandlerSelectors.withClassAnnotation(RestController.class)))
                .build();
    }
    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("妈妈100-act API")
                //创建人
                //.contact(new Contact("Ricky", "http://www.bytebeats.com", "ricky_feng@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description(""
                        + "<br>系统名称：妈妈100活动"
                        + "<br>定位：属于应用和基础服务的中间层，基于SpringBoot的RESTFUL风格的web服务。"
                        + "")
                .build();
    }

}
