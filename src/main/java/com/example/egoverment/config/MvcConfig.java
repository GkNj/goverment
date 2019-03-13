package com.example.egoverment.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/administrative/punch_lock.html");
        registry.addViewController("/administrative/person_ruler.html");
        registry.addViewController("/administrative/lock_time.html");
        registry.addViewController("/news/release_news.html");
        registry.addViewController("/news/news_list.html");

        registry.addRedirectViewController("/index","templates/index.html");
    }
}
