package com.cuihui.consumer.consumerdemo.model;

import java.io.Serializable;

public class User implements Serializable {
    private String uuid;
    private String uname;
    private String age;

    public String getUuid() {
        return uuid;
    }

    public void setUid(String uuid) {
        this.uuid = uuid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uuid + '\'' +
                ", uname='" + uname + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
