package com.feature.resources.server.domain;


import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
public class CheckStatus extends ResourceEntity{
    private String result;

    @Reference
    private Graphic graphic;

    @Reference
    private User user;

    public CheckStatus(){
        setType("CheckStatus");
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }
}
