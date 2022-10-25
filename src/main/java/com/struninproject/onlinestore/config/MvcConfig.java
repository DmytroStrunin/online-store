package com.struninproject.onlinestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@code MvcConfig} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/product/products");
        registry.addViewController("/login").setViewName("forward:/product/products");
        registry.addViewController("/p").setViewName("/product");
    }
}