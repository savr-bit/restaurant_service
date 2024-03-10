package org.example.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "food_order")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position_number")
    private Integer positionNumber;

    @Column(name = "total_amount")
    private Integer total_amount;

    @Column(name = "status")
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FoodOrder(Integer total_amount,User user, Integer positionNumber, String status) {
        this.total_amount = total_amount;
        this.user = user;
        this.positionNumber = positionNumber;
        this.status = status;
    }

    public FoodOrder() {

    }

    public Integer getId() {
        return this.id;
    }

    public Integer getTotal_amount() {
        return this.total_amount;
    }

    public User getUser() {
        return this.user;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getPositionNumber() {
        return this.positionNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPositionNumber(Integer positionNumber) {
        this.positionNumber = positionNumber;
    }

}
