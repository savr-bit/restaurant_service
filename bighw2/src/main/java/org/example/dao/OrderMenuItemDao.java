package org.example.dao;

import org.example.entity.MenuItem;
import org.example.entity.OrderMenuItem;
import org.example.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.example.entity.FoodOrder;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class OrderMenuItemDao implements Dao<OrderMenuItem>{
    public OrderMenuItem findById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        OrderMenuItem orderMenuItem = session.get(OrderMenuItem.class, id);
        session.close();
        return orderMenuItem;
    }

    public void save(OrderMenuItem orderMenuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(orderMenuItem);
        tx1.commit();
        session.close();
    }

    public Integer saveAndReturnId(OrderMenuItem orderMenuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Integer id = (Integer) session.save(orderMenuItem);
        tx1.commit();
        session.close();
        return id;
    }

    public void update(OrderMenuItem orderMenuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(orderMenuItem);
        tx1.commit();
        session.close();
    }

    public void delete(OrderMenuItem orderMenuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(orderMenuItem);
        tx1.commit();
        session.close();
    }

    public long CountOrderPosition(Integer order_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        long res = session.createQuery("select count(*) from order_menu_item where foodOrder.id=" +order_id,long.class).uniqueResult();
        session.close();
        return res;
    }

    public long CountDoneOrderPosition(Integer order_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        long res = session.createQuery("select count(*) from order_menu_item where foodOrder.id=" + order_id + " and status like 'Готово'", long.class).uniqueResult();
        session.close();
        return res;
    }

    public List<MenuItem> GetPositions(Integer order_id) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        List<MenuItem> res = (List<MenuItem>) session.createQuery("select menuItem from order_menu_item where foodOrder.id=" + order_id + "group by menuItem", MenuItem.class).list();
        session.close();
        return res;
    }

    public Long GetNumberOfPositionInOrder(Integer order_id, Integer menu_item_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long num = session.createQuery("select count(*) from order_menu_item where foodOrder.id=" + order_id + "and menuItem.id=" + menu_item_id, Long.class).uniqueResult();
        session.close();
        return num;
    }

    public List<OrderMenuItem> GetOrderDonePositions(Integer order_id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderMenuItem> res = session.createQuery("From OrderMenuItem where foodOrder.id=" + order_id + "and status like 'Готово'").list();
        session.close();
        return res;
    }



    public List<OrderMenuItem> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderMenuItem> orderMenuItems = (List<OrderMenuItem>)  session.createQuery("From OrderMenuItem").list();
        session.close();
        return orderMenuItems;
    }
}