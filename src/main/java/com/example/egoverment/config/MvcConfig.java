package com.example.egoverment.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/punch_lock.html");
        registry.addViewController("/person_ruler.html");
        registry.addViewController("/lock_time.html");
        registry.addViewController("/release_news.html");
        registry.addViewController("/news/news_list.html");
        registry.addViewController("/file/exchequer_file_list.html");
        registry.addViewController("/file/city_file_list.html");
        registry.addViewController("/file/logistics_file_list.html");
        registry.addViewController("/file/news_file_list.html");
        registry.addViewController("/file/traffic_file_list.html");
        registry.addViewController("/file/government_file_list.html");
        registry.addViewController("/document/official_document_design.html");
        registry.addViewController("/document/official_document_list.html");
        registry.addViewController("/document/official_document_upload.html");
        registry.addViewController("/changeImage.html");
        registry.addViewController("/punch_lock.html");
        registry.addViewController("/administrative/updatePassword.html");
        registry.addViewController("/news/page_news_item.html");
        registry.addViewController("/news_detail.html");
        registry.addViewController("/echarts_File.html");
        registry.addViewController("/echarts_dept.html");
        registry.addViewController("/index.html");


        registry.addRedirectViewController("/index","templates/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/login/images");

    }
}
