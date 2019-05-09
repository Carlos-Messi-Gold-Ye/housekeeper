package com.housekeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yezy
 * @since 2019/3/7
 */
@Entity
@Table(name = "HOUSE_USER")
public class HouseUser {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(name = "LOGIN_NAME")
    private String loginName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseUser)) return false;

        HouseUser houseUser = (HouseUser) o;

        return getUserId() != null ? getUserId().equals(houseUser.getUserId()) : houseUser.getUserId() == null;
    }

    @Override
    public int hashCode() {
        return getUserId() != null ? getUserId().hashCode() : 0;
    }
}
