package org.example.dao;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.example.entity.User;
import org.hibernate.Transaction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserDao implements Dao<User>{
    public User findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User ans = session.get(User.class, id);
        session.close();
        return ans;
    }

    public User findByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User ans = session.createQuery("From person where login like '" + login + "'", User.class).list().getFirst();
        session.close();
        return ans;
    }

    public boolean LoginIsBusy(String login) {
        try {
            User user = findByLogin(login);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public List<User> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = (List<User>)  session.createQuery("From User").list();
        return users;
    }
}
