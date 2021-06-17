package com.excilys.cdb.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CompanyRepository {

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;

    public CompanyRepository(SessionFactory sessionFactory) {
        this.entityManager = sessionFactory.createEntityManager();
        this.queryFactory = new JPAQueryFactory(this.entityManager);
    }

    public List<Company> findAll() {
        return this.queryFactory.selectFrom(QCompany.company).fetch();
    }

    public Optional<Company> findById(Integer id) {
        QCompany company = QCompany.company;
        return Optional.ofNullable(this.queryFactory.selectFrom(company).where(company.id.eq(id)).fetchOne());
    }

    @Transactional
    public boolean delete(Integer idCompany) {
        QComputer computer = QComputer.computer;
        QCompany company = QCompany.company;
        boolean deleted = false;

        this.entityManager.getTransaction().begin();
        this.queryFactory.delete(computer).where(computer.company.id.eq(idCompany)).execute();
        deleted = this.queryFactory.delete(company).where(company.id.eq(idCompany)).execute() == 1;
        this.entityManager.getTransaction().commit();
        return deleted;
    }
}
