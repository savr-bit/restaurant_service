package org.example.dao;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.example.entity.MenuItem;
import org.hibernate.Transaction;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class MenuItemDao implements Dao<MenuItem>{
    public MenuItem findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MenuItem menuItem = session.get(MenuItem.class, id );
        session.close();
        return menuItem;
    }

    public void save(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(menuItem);
        tx1.commit();
        session.close();
    }

    public void update(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(menuItem);
        tx1.commit();
        session.close();
    }

    public void delete(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(menuItem);
        tx1.commit();
        session.close();
    }

    public List<MenuItem> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<MenuItem> users = (List<MenuItem>)  session.createQuery("From MenuItem").list();
        session.close();
        return users;
    }

    public Long getSize() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long size = session.createQuery("select count(*) from MenuItem", Long.class).uniqueResult();
        session.close();
        return size;
    }


    public synchronized boolean TryReserveOneItem(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Integer quantity = session.createQuery("select menuItem.quantity from MenuItem menuItem where id=" + id, Integer.class).uniqueResult();
        if (quantity > 0) {

            Transaction tx = session.beginTransaction();
            session.createQuery("update MenuItem set quantity=" + (quantity - 1) + " where id =" + id).executeUpdate();
            tx.commit();
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }
    }
}
