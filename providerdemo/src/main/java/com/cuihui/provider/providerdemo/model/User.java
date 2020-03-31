package com.cuihui.provider.providerdemo.model;

import org.apache.struts2.ServletActionContext;

import java.io.Serializable;

public class User implements Serializable {
    private String uuid;
    private String uname;
    private String age;
    //服务端口,区分集群服务
    private int serverPort;
    //微服务标识
    private String desc = "服务提供者的服务";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = ServletActionContext.getRequest().getServerPort();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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
                "uuid='" + uuid + '\'' +
                ", uname='" + uname + '\'' +
                ", age='" + age + '\'' +
                ", serverPort=" + serverPort +
                ", desc='" + desc + '\'' +
                '}';
    }
}
