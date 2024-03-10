package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.query.Order;

import java.util.List;

@Entity(name = "MenuItem")
@Table(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "item_name")
    private String item_name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "timetocook")
    private Integer timeToCook; // in seconds

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "menuItem")
    private List<OrderMenuItem> orderMenuItems;

    @PreRemove
    private void preRemove() {
        for (OrderMenuItem orderMenuItem : orderMenuItems) {
            orderMenuItem.setMenuItem(null);
        }
    }

    public MenuItem(String item_name, Integer price, Integer timeToCook, Integer quantity) {
        this.item_name = item_name;
        this.price = price;
        this.timeToCook = timeToCook;
        this.quantity = quantity;
    }

    public MenuItem() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getItem_name() {
        return this.item_name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Integer getTimeToCook() {
        return this.timeToCook;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTimeToCook(Integer timeToCook) {
        this.timeToCook = timeToCook;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
