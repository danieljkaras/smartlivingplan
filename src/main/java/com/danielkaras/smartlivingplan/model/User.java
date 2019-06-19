package com.danielkaras.smartlivingplan.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Table(name = "tabuser")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private Integer familyId;

    @Column(name = "ismemberoffamily")
    private Boolean isMemberOfFamily;

    @Column(name = "isadmin")
    private Boolean isAdmin;

    @ManyToMany(mappedBy = "users")
    private List<Category> categories;

    @ManyToMany(mappedBy = "users")
    private List<Payment> payments;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Boolean getMemberOfFamily() {
        return isMemberOfFamily;
    }

    public void setMemberOfFamily(Boolean memberOfFamily) {
        isMemberOfFamily = memberOfFamily;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", familyId=" + familyId +
                ", isMemberOfFamily=" + isMemberOfFamily +
                ", isAdmin=" + isAdmin +
                ", categories=" + categories +
                '}';
    }
}
