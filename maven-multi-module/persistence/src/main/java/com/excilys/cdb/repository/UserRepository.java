package com.excilys.cdb.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.QUser;
import com.excilys.cdb.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserRepository {

    @Autowired
    PasswordEncoder passwordEncoder;

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;

    public UserRepository(SessionFactory sessionFactory) {
        this.entityManager = sessionFactory.createEntityManager();
        this.queryFactory = new JPAQueryFactory(this.entityManager);
    }

    public Optional<User> findByUsername(String username) {
        QUser user = QUser.user;
        return Optional.ofNullable(queryFactory.selectFrom(user).where(user.username.eq(username)).fetchOne());
    }
}
