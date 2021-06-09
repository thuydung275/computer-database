package com.excilys;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.AppConfig;
import com.excilys.repository.CompanyRepository;
import com.excilys.repository.ComputerRepository;

public class App {
    private static Logger log = Logger.getLogger(App.class);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
//        companyRepository.getList().forEach(c -> log.debug(c));
//        Optional<Company> company = companyRepository.findById(10);
//        log.debug(company.get());

        ComputerRepository computerRepository = context.getBean(ComputerRepository.class);
        // computerRepository.getList().forEach(c -> log.debug(c));
        // Optional<Computer> computer = computerRepository.findById(555);
        // log.debug(computer.get());
//        Computer computer = computerRepository.findById(584).get();
//        Company company = companyRepository.findById(10).get();
//        computer.setCompany(company);
        boolean deleted = computerRepository.delete(586);
        log.debug("========deleted computer =================");
        log.debug(deleted);
    }

}
