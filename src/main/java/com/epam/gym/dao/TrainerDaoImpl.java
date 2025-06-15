package com.epam.gym.dao;

import com.epam.gym.domain.Trainer;
import java.util.Collection;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Trainer save(Trainer trainer) {
        session().saveOrUpdate(trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        String hql = "from Trainer t join fetch t.user u where u.username = :un";
        return session()
                .createQuery(hql, Trainer.class)
                .setParameter("un", username)
                .uniqueResultOptional();
    }

    @Override
    public Collection<Trainer> findAll() {
        return session()
                .createQuery("from Trainer", Trainer.class)
                .list();
    }

    @Override
    public void deleteByUsername(String username) {
        findByUsername(username).ifPresent(session()::delete);
    }

    @Override
    public void deleteAll() {
        session()
                .createQuery("delete from Trainer")
                .executeUpdate();
    }
}
