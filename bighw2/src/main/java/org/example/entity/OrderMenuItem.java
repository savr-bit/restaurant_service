package org.example.entity;


import jakarta.persistence.*;

@Entity(name = "order_menu_item")
public class OrderMenuItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private FoodOrder foodOrder;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "status")
    private String status;




    public OrderMenuItem(FoodOrder foodOrder, MenuItem menuItem, String status) {
        this.foodOrder = foodOrder;
        this.menuItem = menuItem;
        this.status = status;
    }

    public OrderMenuItem() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public FoodOrder getFoodOrder() {
        return this.foodOrder;
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
