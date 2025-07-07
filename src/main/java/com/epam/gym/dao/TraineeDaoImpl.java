package com.epam.gym.dao;

import com.epam.gym.domain.Trainee;
import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        // uses Spring’s current transactional session
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Trainee save(Trainee t) {
        session().saveOrUpdate(t);
        return t;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        var hql = "from Trainee t join fetch t.user u where u.username = :un";
        var query = session().createQuery(hql, Trainee.class)
                .setParameter("un", username);
        return query.uniqueResultOptional();
    }

    @Override
    public Collection<Trainee> findAll() {
        return session().createQuery("from Trainee", Trainee.class)
                .list();
    }

    @Override
    public void deleteByUsername(String username) {
        findByUsername(username).ifPresent(session()::delete);
    }

    @Override
    public void deleteAll() {
        session().createQuery("delete from Trainee").executeUpdate();

    }

}
