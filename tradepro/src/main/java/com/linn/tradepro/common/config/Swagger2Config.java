package com.linn.tradepro.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration //标记配置类
@EnableOpenApi //开启Swagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi(){
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com"))
            .paths(PathSelectors.any())
            .build();
    }

    //API文档的详细信息函数
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("TradePro API文档")
                .description("TradePro")
                .version("1.0")
                .build();
    }
}
