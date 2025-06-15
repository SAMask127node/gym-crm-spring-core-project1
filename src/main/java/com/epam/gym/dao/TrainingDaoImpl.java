package com.epam.gym.dao;

import com.epam.gym.domain.Training;
import java.util.Collection;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Training save(Training training) {
        session().saveOrUpdate(training);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        String hql = "from Training t where t.id = :id";
        return session()
                .createQuery(hql, Training.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    @Override
    public Collection<Training> findAll() {
        return session()
                .createQuery("from Training", Training.class)
                .list();
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(session()::delete);
    }

    @Override
    public void deleteAll() {
        session()
                .createQuery("delete from Training")
                .executeUpdate();
    }
}
