package com.example.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.example.beans.ElasticSearchConnect;
import com.example.beans.JedisBean;
import com.example.beans.MyJsonValidator;

@Configuration
public class RedisConfig {

	@Bean("validator")
	public MyJsonValidator myJsonValidator() {
		return new MyJsonValidator(jedisBean()) ;
	}
	
	@Bean("jedisBean")
	public JedisBean jedisBean() {
		return new JedisBean() ;
	}
	
	@Bean("elasticSearchConnect")
	public ElasticSearchConnect elasticSearchConnect() {
		return new ElasticSearchConnect() ;
	}
	
    @Bean
	public FilterRegistrationBean<ShallowEtagHeaderFilter> filterReg() {
		final FilterRegistrationBean<ShallowEtagHeaderFilter> reg =  new FilterRegistrationBean<>();
		reg.setFilter((ShallowEtagHeaderFilter) etagFilter());
		reg.addUrlPatterns("/Plan/*");
		reg.setName("etagFilter");
		reg.setOrder(1);
		System.out.println("inside filter registration bean");
		return reg;
	}
	
	@Bean(name="etagFilter")
	public Filter etagFilter() {
		System.out.println("inside e tag filter");
		return new ShallowEtagHeaderFilter();
	} 
	
}
