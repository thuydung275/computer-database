package com.excilys;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.AppConfig;
import com.excilys.dao.CompanyDAO;

public class App {
    private static Logger log = Logger.getLogger(App.class);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CompanyDAO companyDao = context.getBean(CompanyDAO.class);
        log.debug(companyDao.findById(1));
    }

}
