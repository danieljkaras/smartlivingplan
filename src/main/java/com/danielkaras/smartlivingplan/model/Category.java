package com.danielkaras.smartlivingplan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tabcategory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "ispayment")
    private Boolean isPayment;

    @Column(name = "isincome")
    private Boolean isIncome;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Payment> payments;

    @ManyToMany
    @JoinTable(name = "relcategoryuser",
            joinColumns = @JoinColumn(name = "idcategory", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "iduser", referencedColumnName = "id"))
    @JoinColumn(name = "iduser", referencedColumnName = "id")
    @JsonIgnore
    private List<User> users;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPayment=" + isPayment +
                ", isIncome=" + isIncome +
                '}';
    }
}
