package android.myapplicationdev.com.dmsdchatapp;

import java.io.Serializable;

/**
 * Created by 15056158 on 17/8/2017.
 */

public class User implements Serializable {
    private String id;
    private String name;

    public User(){
    }

    public User(String name) {
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
