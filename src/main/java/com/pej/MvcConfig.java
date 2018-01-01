package com.pej;


import com.pej.web.FormateurFormatter;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	 registry.addViewController("/").setViewName(
                 "forward:login.html");
         registry.addViewController("pej/login").setViewName("login");
         registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public DozerBeanMapperFactoryBean getDozerMapper() throws Exception{
        DozerBeanMapperFactoryBean returnValue= new DozerBeanMapperFactoryBean();
        return returnValue;
    }
}