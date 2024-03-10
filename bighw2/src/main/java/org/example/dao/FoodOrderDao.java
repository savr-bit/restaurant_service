package org.example.dao;

import org.example.entity.OrderMenuItem;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.example.entity.FoodOrder;
import org.hibernate.Transaction;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class FoodOrderDao implements Dao<FoodOrder>{
    public FoodOrder findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FoodOrder foodOrder = session.get(FoodOrder.class, id);
        session.close();
        return foodOrder;
    }

    public void save(FoodOrder foodOrder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(foodOrder);
        tx1.commit();
        session.close();
    }

    public Integer saveAndReturnId(FoodOrder foodOrder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Integer id = (Integer) session.save(foodOrder);
        tx1.commit();
        session.close();
        return id;
    }

    public void update(FoodOrder foodOrder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(foodOrder);
        tx1.commit();
        session.close();
    }

    public void delete(FoodOrder foodOrder) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(foodOrder);
        tx1.commit();
        session.close();
    }

    public synchronized void saveOrUpdate(FoodOrder foodOrder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(foodOrder);
        tx1.commit();
        session.close();
    }

    public List<FoodOrder> GetUserOrders(Integer user_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FoodOrder> foodOrderList = (List<FoodOrder>) session.createQuery("from food_order where user.id=" + user_id).list();
        session.close();
        return foodOrderList;
    }

    public List<FoodOrder> GetUserDoneOrders(Integer user_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FoodOrder> foodOrderList = (List<FoodOrder>) session.createQuery("from food_order where user.id=" + user_id + "and status like 'Готово'").list();
        session.close();
        return foodOrderList;
    }


    public List<FoodOrder> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FoodOrder> foodOrders = (List<FoodOrder>)  session.createQuery("From food_order").list();
        session.close();
        return foodOrders;
    }
}