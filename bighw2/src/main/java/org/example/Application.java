package org.example;


import org.example.dao.FoodOrderDao;
import org.example.dao.MenuItemDao;
import org.example.dao.OrderMenuItemDao;
import org.example.entity.FoodOrder;
import org.example.entity.MenuItem;
import org.example.entity.OrderMenuItem;
import org.example.entity.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.example.dao.UserDao;
import org.hibernate.query.Order;

public class Application {

    private Scanner in = new Scanner(System.in);
    private UserDao userDao = new UserDao();
    private MenuItemDao menuItemDao = new MenuItemDao();

    private FoodOrderDao foodOrderDao = new FoodOrderDao();
    private OrderMenuItemDao orderMenuItemDao = new OrderMenuItemDao();


    private ExecutorService excutorService = Executors.newFixedThreadPool(5);


    private User user;
    public Application() {}

    public void run() {
        if (auth()) {
            if (user.getUserType().equals("customer")) {
                while (true) {
                    System.out.println("Добро пожаловать, " + user.getName());
                    System.out.println("Доступные опции: ");
                    System.out.println("1 - Сделать заказ");
                    System.out.println("2 - Мои заказы");
                    System.out.println("3 - Изменить заказ");
                    System.out.println("4 - Оплатить заказ");
                    System.out.println("q - Выйти");

                    System.out.println("");
                    System.out.print("Ваш выбор>>> ");
                    String choice = in.nextLine();
                    System.out.println();

                    switch (choice) {
                        case("1"):
                            makeOrder();
                            break;
                        case("2"):
                            showAllOrders();
                            break;
                        case("3"):
                            changeOrder();
                            break;
                        case("4"):
                            payOrder();
                            break;
                        case("q"):
                            System.out.println("Выход...");
                            System.exit(0);
                        default:
                            System.out.println("Пожалуйста выберите одну из доступных опций");

                    }




                }
            } else {
                while (true) {
                    System.out.println("Добро пожаловать, " + user.getName());
                    System.out.println("Доступные опции: ");
                    System.out.println("1 - Добавить блюдо в меню");
                    System.out.println("2 - Удалить блюдо из меню");
                    System.out.println("3 - Показать меню");
                    System.out.println("q - Выйти");
                    System.out.println("");
                    System.out.print("Ваш выбор>>> ");
                    String choice = in.nextLine();
                    System.out.println();

                    switch (choice) {
                        case ("1"):
                            addNewDish();
                            break;
                        case ("2"):
                            deleteDish();
                            break;
                        case ("3"):
                            showAllMenu();
                            break;
                        case("q"):
                            System.out.println("Выход...");
                            System.exit(0);
                        default:
                            System.out.println("Пожалуйста выберите одну из доступных опций");
                    }

                }
            }


        }
    }

    public boolean auth() {
        while (true) {
            System.out.println("Добро пожаловать!");
            System.out.println("Выберите одну из двух опций:");
            System.out.println("1 - Войти");
            System.out.println("2 - Зарегистрироваться");
            System.out.println("3 - Завершить работу программы");

            System.out.print("Ваш выбор>>> ");

            String choice = in.nextLine();

            if (choice.equals("1")) {
                if (loginInSystem()) {
                    return true;
                }
            } else if (choice.equals("2")) {
                registration();
            } else if (choice.equals("3")){
                System.out.println("Выход...");
                System.exit(0);
                break;
            }
        }
        return true;
    }

    public boolean loginInSystem() {
        System.out.println("Введите логин:");
        System.out.print(">>> ");
        String login = in.nextLine();
        if (login.isEmpty()) {
            System.out.println("Логин не может быть пустой строкой");
            return false;
        }

        System.out.println("Введите свой пароль:");
        System.out.print(">>> ");
        String password = in.nextLine();
        if (password.isEmpty()) {
            System.out.println("Пароль не может быть пустой строкой");
            return false;
        }

        try {
            this.user = userDao.findByLogin(login);
            if (user.getPasswordHash().equals(Encryption.toHexString(Encryption.getSHA(password)))) {
                return true;
            } else {
                user = null;
                System.out.println("Неправильно введен логин или пароль");
                return false;
            }

        } catch (NoSuchElementException e1) {
            System.out.println("Неправильно введен логин или пароль");
        }
        return false;
    }

    public void registration() {
        System.out.println("Введите свой логин: ");
        System.out.print(">>> ");
        String login = in.nextLine();
        if (login.isEmpty()) {
            System.out.println("Логин не может быть пустой строкой");
            return;
        }
        if (userDao.LoginIsBusy(login)) {
            System.out.println("Пользователь с таким логином уже существует");
            return;
        }

        System.out.println("Введите свое имя: ");
        String name = in.nextLine();
        if (name.isEmpty()) {
            System.out.println("Имя не может быть пустой строкой");
            return;
        }

        System.out.println("Введите свой пароль: ");
        System.out.print(">>> ");
        String password = in.nextLine();
        if (password.isEmpty()) {
            System.out.println("Пароль не может быть пустой строкой");
            return;
        }
        String passwordHash = Encryption.toHexString(Encryption.getSHA(password));

        User new_user = new User(name, login, passwordHash, "customer");
        userDao.save(new_user);
        System.out.println("Пользователь успешно зарегестрирован");
    }

    public void addNewDish() {
        System.out.println("Введите название блюда: ");
        System.out.print(">>> ");
        String name = in.nextLine();
        if (name.isEmpty()) {
            System.out.println("Название блюда не может быть пустой строкой");
            return;
        }

        System.out.println("Введите цену блюда: ");
        System.out.print(">>> ");
        Integer cost;
        try {
            cost = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Цена должна быть целым числом");
            return;
        }

        System.out.println("Введите время приготовления в секундах:");
        System.out.print(">>> ");
        Integer timeToCook;
        try {
            timeToCook = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Время приготовления должно быть в секундах");
            return;
        }

        System.out.println("Введите количество:");
        System.out.print(">>> ");
        Integer quantity;
        try {
            quantity = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Количество должно быть целым числом");
            return;
        }

        menuItemDao.save(new MenuItem(name, cost, timeToCook, quantity));
        System.out.println("Блюдо успешно добавлено");
    }

    public void deleteDish() {
        List<MenuItem> menuItemList = menuItemDao.getAll();
        showMenuList(menuItemList);
        System.out.println("Выберите номер блюда для удаления:");
        System.out.print(">>> ");
        Integer num;
        try {
            num = Integer.parseInt(in.nextLine());
            if (!(num >= 1 && num <= menuItemDao.getSize())) {
                System.out.println("Такого номера нет");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("Вы ввели не число");
            return;
        }

        menuItemDao.delete(menuItemList.get(num - 1));
        System.out.println("Блюдо успешно удалено из меню");
    }

    public void showMenuList(List<MenuItem> menuItemList) {
        if (menuItemList.size() == 0) {
            System.out.println("Блюд в меню нет");
        }
        int counter = 1;
        for (MenuItem menuItem : menuItemList) {
            System.out.println(counter++ + ". Название: " + menuItem.getItem_name() + "\tЦена: " + menuItem.getPrice() + "\tВремя приготовления: " + menuItem.getTimeToCook());
        }
    }

    public void showAllMenu() {
        showMenuList(menuItemDao.getAll());
        System.out.println("Нажмите на любую клавишу, чтобы вернуться назад");
        String stub = in.nextLine();
    }

    public void makeOrder() {
        List<Integer> menuItemsId = getListOfMenuItems();
        System.out.println("Подтвердите свой заказ (введите <<да>>):");
        System.out.print(">>> ");
        String conf = in.nextLine();
        if (!conf.equals("да")) {
            System.out.println("Отменяем заказ...");
            return;
        }
        Integer totalAmount = 0;
        for (int i = 0; i < menuItemsId.size(); ++i) {
            totalAmount += menuItemDao.findById(menuItemsId.get(i)).getPrice();
        }
        Integer foodOrderId = foodOrderDao.saveAndReturnId(new FoodOrder(totalAmount, user, menuItemsId.size(), "Обрабатывается"));

        for (int i = 0; i < menuItemsId.size(); ++i) {
            excutorService.submit(new cookingDish(menuItemsId.get(i), foodOrderId));
        }

        System.out.println("Заказ поступил в обработку (статус заказа перешел в <<Обрабатывается>>");

    }



    List<Integer> getListOfMenuItems() {
        List<MenuItem> allMenu = menuItemDao.getAll();
        List<Integer> userChoice = new ArrayList<>();
        while (true) {
            showMenuList(allMenu);
            System.out.println("Для того, чтобы завершить заказ, введите q");
            System.out.println("Введите номер блюда, которое хотите выбрать");
            System.out.print(">>> ");
            Integer num;
            try {
                String input = in.nextLine();
                if (input.equals("q")) {
                    break;
                }
                num = Integer.parseInt(input);
                if (!(num >= 1 && num <= menuItemDao.getSize())) {
                    System.out.println("Такого номера нет");
                    continue;
                }
            } catch (NumberFormatException e){
                System.out.println("Вы ввели не число");
                continue;
            }
            System.out.println("Введите количество (осталось " + allMenu.get(num - 1).getQuantity() +") :");
            System.out.print(">>> ");
            Integer quantity = 0;

            try {
                quantity = Integer.parseInt(in.nextLine());
                if (!(quantity >= 1 && quantity <= allMenu.get(num - 1).getQuantity())) {
                    System.out.println("Недопустимое количество");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число");
            }

            for (int i = 0; i < quantity; ++i) {
                userChoice.add(allMenu.get(num - 1).getId());
            }
            allMenu.get(num - 1).setQuantity(allMenu.get(num - 1).getQuantity() - quantity);
        }
        return userChoice;
    }

    public void showOrders(List<FoodOrder> foodOrderList) {
        int counter = 1;
        for (FoodOrder foodOrder : foodOrderList) {
            System.out.println(counter++ +"."+"Статус:" + foodOrder.getStatus());
            System.out.println("Блюда в заказе: ");

            List<MenuItem> orderMenuItemList = orderMenuItemDao.GetPositions(foodOrder.getId());
            for (int i = 0; i < orderMenuItemList.size(); ++i) {
                System.out.println((i + 1) + ". " + orderMenuItemList.get(i).getItem_name() + " Количество: " + orderMenuItemDao.GetNumberOfPositionInOrder(foodOrder.getId(), orderMenuItemList.get(i).getId()));
            }
            System.out.println();
        }
    }

    public void changeOrder() {
        List<FoodOrder> userOrderMenuList = foodOrderDao.GetUserOrders(user.getId());
        showOrders(userOrderMenuList);
        System.out.println("Выберите заказ: ");
        Integer chose = 0;
        try {
            chose = Integer.parseInt(in.nextLine());
            if (!(chose >= 1 && chose <= userOrderMenuList.size())) {
                System.out.println("Недопустимое количество");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели не число");
            return;
        }

        System.out.println("Выберите действие:");
        System.out.println("1. Отменить заказ");
        System.out.println("2. Добавить позиции в меню");
        System.out.print(">>> ");
        Integer chose2 = 0;
        try {
            chose2 = Integer.parseInt(in.nextLine());
            if (!(chose2 >= 1 && chose2 <= 2)) {
                System.out.println("Недопустимый номер");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели не число");
            return;
        }

        if (chose2 == 1) {
            System.out.println(userOrderMenuList.get(chose - 1).getStatus());
            if (userOrderMenuList.get(chose - 1).getStatus().trim().equals("Готово") ||
                    userOrderMenuList.get(chose - 1).getStatus().trim().equals("Оплачен")) {
                System.out.println("Заказ уже готов, отменить не получиться");
            } else if (userOrderMenuList.get(chose - 1).getStatus().trim().equals("Отменен")) {
                System.out.println("Заказ уже отменен, отменить не получиться");
            } else {
                userOrderMenuList.get(chose - 1).setStatus("Отменен");
                foodOrderDao.update(userOrderMenuList.get(chose - 1));
                List<OrderMenuItem> orderMenuItemList = orderMenuItemDao.GetOrderDonePositions(userOrderMenuList.get(chose - 1).getId());
                for (OrderMenuItem orderMenuItem : orderMenuItemList) {
                    orderMenuItem.setStatus("Отменен");
                    orderMenuItemDao.update(orderMenuItem);
                }

                System.out.println("Заказ успешно отменен");
            }

        } else {
            if (userOrderMenuList.get(chose - 1).getStatus().trim().equals("Обрабатывается") ||
                    userOrderMenuList.get(chose - 1).getStatus().trim().equals("Готовится"))  {
                List<Integer> menuItemIdList = getListOfMenuItems();
                userOrderMenuList.get(chose - 1).setPositionNumber(userOrderMenuList.get(chose - 1).getPositionNumber() + menuItemIdList.size());
                foodOrderDao.update(userOrderMenuList.get(chose-1));
                for (int i = 0; i < menuItemIdList.size(); ++i) {
                    excutorService.submit(new cookingDish(menuItemIdList.get(i), userOrderMenuList.get(chose - 1).getId()));
                }
            } else {
                System.out.println("Заказ уже не готовится, добавить позиции нельзя");
            }

        }
    }


    public void payOrder() {
        List<FoodOrder> foodOrderList = foodOrderDao.GetUserDoneOrders(user.getId());
        showOrders(foodOrderList);
        System.out.println("Выберите заказ для оплаты:");
        System.out.print(">>> ");
        Integer chose;
        try {
            chose = Integer.parseInt(in.nextLine());
            if (!(chose >= 1 && chose <= foodOrderList.size())) {
                System.out.println("Неправильный номер");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели не число");
            return;
        }

        System.out.println("К оплате " + foodOrderList.get(chose - 1).getTotal_amount() + " рублей");
        System.out.println("Выберите способ оплаты:");
        System.out.println("1.Наличными");
        System.out.println("2.Банковской картой");
        System.out.print(">>> ");
        Integer chose2;
        try {
            chose2 = Integer.parseInt(in.nextLine());
            if (!(chose2 >= 1 && chose2 <= 2)) {
                System.out.println("Неправильный номер");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели не число");
            return;
        }
        foodOrderList.get(chose - 1).setStatus("Оплачен");
        foodOrderDao.update(foodOrderList.get(chose - 1));
        System.out.println("Заказ оплачен");
    }




    public void showAllOrders() {
        List<FoodOrder> userOrderMenuList = foodOrderDao.GetUserOrders(user.getId());
        showOrders(userOrderMenuList);
        System.out.println("Введите что-нибудь, чтобы вернутьсян назад");
        String stub = in.nextLine();
    }


    class cookingDish implements Runnable {
        private Integer menuItemId;
        private Integer foodOrderId;
        public cookingDish(Integer menuItemId, Integer foodOrderId) {
            this.menuItemId = menuItemId;
            this.foodOrderId = foodOrderId;
        }
        public void run()
        {
            FoodOrder foodOrder = foodOrderDao.findById(foodOrderId);
            if (foodOrder.getStatus().equals("Отменен")) {
                return;
            }
            if (menuItemDao.TryReserveOneItem(menuItemId)) {
                MenuItem menuItem = menuItemDao.findById(menuItemId);
                try {
                    Integer orderMenuItemId = orderMenuItemDao.saveAndReturnId(new OrderMenuItem(foodOrder, menuItem,"Готовится"));
                    foodOrder.setStatus("Готовится");
                    foodOrderDao.saveOrUpdate(foodOrder);
                    Thread.sleep(menuItem.getTimeToCook() * 1000);
                    foodOrder = foodOrderDao.findById(foodOrderId);
                    if (foodOrder.getStatus().equals("Отменен")) {
                        return;
                    }
                    OrderMenuItem orderMenuItem = orderMenuItemDao.findById(orderMenuItemId);
                    orderMenuItem.setStatus("Готово");
                    orderMenuItemDao.update(orderMenuItem);

                    System.out.println(orderMenuItemDao.CountOrderPosition(foodOrderId));
                    if (orderMenuItemDao.CountDoneOrderPosition(foodOrderId) == foodOrder.getPositionNumber()) {
                        foodOrder.setStatus("Готово");
                        foodOrderDao.saveOrUpdate(foodOrder);
                    }
                } catch(InterruptedException e) {
                }
            } else {
                foodOrder.setStatus("Отменен");
                foodOrderDao.saveOrUpdate(foodOrder);
            }
        }
    }
}


