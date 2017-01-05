package com.avinash.moneylimit.Models;

/**
 * Created by avinash on 1/5/2017.
 */

public class Friend {
    String uid, name;

    public Friend(){
        super();
    }

    public Friend(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
