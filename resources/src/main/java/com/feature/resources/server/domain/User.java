package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.List;

@Entity
public class User extends ResourceEntity {
    private String loginName;
    private String name;
    private String plainPassword;
    private String password;
    private String salt;
    private String email;
    private String status;

    @Reference
    private List<Role> roles = Lists.newArrayList();

    public User(){
        setType("user");
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

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addNewRole(Role role){
        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", getId())
                                            .add("loginName", loginName)
                                            .add("name", name)
                                            .add("plainPassword",plainPassword)
                                            .add("password",password)
                                            .add("salt",salt)
                                            .add("email",email)
                                            .add("status",status)
                                            .add("Roles",roles.toString())
                                            .toString();
    }
}
