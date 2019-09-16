package com.oauth.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by Admin on 2019/6/8.
 */
@Entity
@Table(name = "USER")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Column(name = "account", nullable = true, length = 20)
    private String account;
    @Column(name = "pwd", nullable = true, length = 50)
    private String pwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
