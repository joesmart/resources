package com.feature.resources.server.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午9:49
 * FileName:UserDTO
 */
public class UserDTO {

    @Length(min = 5,max = 30)
    private String name;
    @Length(min = 5,max = 30)
    private String loginName;
    @Email
    private String email;
    @Length(min=5,max=20)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
