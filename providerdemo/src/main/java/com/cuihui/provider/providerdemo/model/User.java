package com.cuihui.provider.providerdemo.model;

import org.apache.struts2.ServletActionContext;

import java.io.Serializable;

public class User implements Serializable {
    private String uuid;
    private String uname;
    private String age;
    //服务端口,区分集群服务
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = ServletActionContext.getRequest().getServerPort();
    }

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
