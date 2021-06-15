package com.excilys;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.AppConfig;
import com.excilys.service.CompanyService;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CompanyService companyService = context.getBean(CompanyService.class);
        companyService.getList().forEach(System.out::println);
    }
}
