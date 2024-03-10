package org.example;

import org.example.dao.FoodOrderDao;
import org.example.dao.MenuItemDao;
import org.example.dao.OrderMenuItemDao;
import org.example.dao.UserDao;
import org.example.entity.FoodOrder;
import org.example.entity.MenuItem;
import org.example.entity.OrderMenuItem;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
















    }


}