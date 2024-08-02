package com.sysxx.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <p>Swagger 配置类</p>
 *
 * @author By: chegnxuyanshitang
 * Package com.demo2.config
 * Ceate Time 2024-04-22 10:25
 */
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler ("/doc.html").addResourceLocations ("classpath:/META-INF/resources/");
        registry.addResourceHandler ("/webjars/**").addResourceLocations ("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public OpenAPI apiInfo () {
        return new OpenAPI ()
                .info (new Info ()
                        .title ("demo服务")
                        .version ("1.0.0")
                        .description ("demo接口")
                        .license (new License ().name ("Apache 2.0")
                                .url ("http://www.yang.com/"))
                );
    }
}