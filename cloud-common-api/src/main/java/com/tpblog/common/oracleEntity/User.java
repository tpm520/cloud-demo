package com.tpblog.common.oracleEntity;


import javax.persistence.*;

@Table(name = "userAccount")
@Entity
public class User {

    @GeneratedValue(generator = "user_test_seq", strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "username", columnDefinition = "varchar(255)")
    private String username;

    @Column(name = "account", columnDefinition = "double")
    private double account;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", account=" + account +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}
