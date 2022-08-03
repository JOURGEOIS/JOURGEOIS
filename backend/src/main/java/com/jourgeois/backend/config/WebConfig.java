package com.jourgeois.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.jourgeois.backend.controller")
public class WebConfig implements WebMvcConfigurer{

    private final String IMG_TMP = "file:/app/DATA/tmp/";
//    private final String PROFILE_TMP = "C:///app/DATA/tmp/profile/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/img/**")
                .addResourceLocations(IMG_TMP).setCachePeriod(60 * 60 * 24 * 365);
    }
}
