package com.danielkaras.smartlivingplan.model;

import com.danielkaras.smartlivingplan.utils.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tabpayment")
public class Payment extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "paymentdate")
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcategory", referencedColumnName = "id", nullable = false)
    private Category category;

    private String description;

    @ManyToMany
    @JoinTable(name = "relpaymentuser",
        joinColumns = @JoinColumn(name = "idpayment", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "iduser" , referencedColumnName = "id"))
    @JsonIgnore
    private List<User> users;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal value;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentDate=" + paymentDate +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", users=" + users +
                ", value=" + value +
                '}';
    }
}
