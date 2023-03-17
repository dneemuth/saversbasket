package com.sb.web.configuration;

import java.util.concurrent.Executor;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = {"com.sb.web.controllers"})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/error").setViewName("forward:/desktop/error.html");
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/images/**",
                "/vendor/**",
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/scss/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/images/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/fonts/",
                        "classpath:/static/scss/",
                        "classpath:/static/vendor/");
    }
	
   @Bean
   public ModelMapper modelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      return modelMapper;
   }
      
   @Bean(name = "asyncExecutor")
   public Executor asyncExecutor() 
   {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(5);
       executor.setMaxPoolSize(10);
       executor.setQueueCapacity(100);     
       executor.setThreadNamePrefix("AsynchThread-");
       executor.initialize();
       return executor;
   }   
   
   @Bean(name = "asyncPurchaseHistoryExecutor")
   public Executor asyncPurchaseHistoryExecutor() 
   {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(3);
       executor.setMaxPoolSize(5);
       executor.setQueueCapacity(10);     
       executor.setThreadNamePrefix("asyncPurchaseHistoryThread-");
       executor.initialize();
       return executor;
   }
   
   @Bean
   public SpringSecurityDialect springSecurityDialect(){
       return new SpringSecurityDialect();
   }  
   
   @Bean
   public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
       return container -> {
           container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
                   "/error"));
       };
     }

}
