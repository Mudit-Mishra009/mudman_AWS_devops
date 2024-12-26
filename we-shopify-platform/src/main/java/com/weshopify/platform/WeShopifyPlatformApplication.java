package com.weshopify.platform;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.weshopify.platform.fascade.StrangularFascade;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class WeShopifyPlatformApplication implements CommandLineRunner{

	@Autowired
	private HikariDataSource hds;
	
	public static void main(String[] args) {
		SpringApplication.run(WeShopifyPlatformApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(strangularFascade());
	    registration.addUrlPatterns("/view-customerBoard/*","/customers/*","/save-customer/*");
	   // registration.addInitParameter("paramName", "paramValue");
	    registration.setName("StrangularFascade");
	    registration.setOrder(1);
	    return registration;
	}
	
	/**
	 * Strangular Fascade Filter
	 * @return
	 */
	public Filter strangularFascade() {
		return new StrangularFascade();
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("command line runner ru methos is executing....");
		System.out.println(hds.getDriverClassName());
		System.out.println(hds.getUsername());
		System.out.println(hds.getJdbcUrl());
		System.out.println(hds.getPassword());
		System.out.println(hds.getSchema());
	}
	
}
