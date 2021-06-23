package com.excilys.cdb.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ComputerRepository {

    private JPAQueryFactory queryFactory;
    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    public ComputerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.entityManager = sessionFactory.createEntityManager();
        this.queryFactory = new JPAQueryFactory(this.entityManager);
    }

    public List<Computer> findAll() {
        return this.queryFactory.selectFrom(QComputer.computer).fetch();
    }

    public List<Computer> findByCriteria(Map<String, String> criteria) {
        System.out.println("==============IN=============");
        QComputer computer = QComputer.computer;
        QCompany company = QCompany.company;
        JPAQuery<Computer> query = this.queryFactory.selectFrom(computer).leftJoin(computer.company, company);
        if (criteria.containsKey("search") && StringUtils.isNotBlank(criteria.get("search"))) {
            query = query.where(computer.name.like("%" + criteria.get("search") + "%")
                    .or(company.name.like("%" + criteria.get("search") + "%")));
        }
        if (criteria.containsKey("order") && StringUtils.isNotBlank(criteria.get("order"))
                && criteria.containsKey("sort") && StringUtils.isNotBlank(criteria.get("sort"))) {
            String order = criteria.get("order");
            String sort = criteria.get("sort");
            if (order.equals("ASC")) {
                switch (sort) {
                case "computerName":
                    query = query.orderBy(computer.name.asc());
                    break;
                case "introduced":
                    query = query.orderBy(computer.introduced.asc());
                    break;
                case "discontinued":
                    query = query.orderBy(computer.discontinued.asc());
                    break;
                case "companyName":
                    query = query.orderBy(computer.company.name.asc());
                    break;
                default:
                    query = query.orderBy(computer.id.asc());
                    break;
                }
            } else if (order.equals("DESC")) {
                switch (sort) {
                case "computerName":
                    query = query.orderBy(computer.name.desc());
                    break;
                case "introduced":
                    query = query.orderBy(computer.introduced.desc());
                    break;
                case "discontinued":
                    query = query.orderBy(computer.discontinued.desc());
                    break;
                case "companyName":
                    query = query.orderBy(computer.company.name.desc());
                    break;
                default:
                    query = query.orderBy(computer.id.desc());
                    break;
                }
            }

        }
        if (criteria.containsKey("limit") && StringUtils.isNumeric(criteria.get("limit"))
                && criteria.containsKey("offset") && StringUtils.isNumeric(criteria.get("offset"))) {
            query = query.limit(Long.parseLong(criteria.get("limit"))).offset(Long.parseLong(criteria.get("offset")));
        }
        return query.fetch();
    }

    public Optional<Computer> findById(Integer id) {
        QComputer computer = QComputer.computer;
        return Optional.ofNullable(this.queryFactory.selectFrom(computer).where(computer.id.eq(id)).fetchOne());
    }

    @Transactional
    public Computer create(Computer computer) {
        Session session;

        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        int id = (Integer) session.save(computer);
        computer.setId(id);
        return computer;
    }

    @Transactional
    public Computer update(Computer computer) {
        QComputer q = QComputer.computer;
        List<? extends Path<?>> paths = new ArrayList<>(Arrays.asList(q.name, q.introduced, q.discontinued, q.company));
        List<?> values = new ArrayList<>(Arrays.asList(computer.getName(), computer.getIntroduced(),
                computer.getDiscontinued(), computer.getCompany()));

        this.entityManager.getTransaction().begin();
        this.queryFactory.update(q).where(q.id.eq(computer.getId())).set(paths, values).execute();
        this.entityManager.getTransaction().commit();
        return computer;
    }

    @Transactional
    public boolean delete(Integer id) {
        QComputer computer = QComputer.computer;
        this.entityManager.getTransaction().begin();
        boolean deleted = this.queryFactory.delete(computer).where(computer.id.eq(id)).execute() == 1;
        this.entityManager.getTransaction().commit();
        return deleted;
    }
}
