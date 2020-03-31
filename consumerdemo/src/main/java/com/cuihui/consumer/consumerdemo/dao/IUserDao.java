package com.cuihui.consumer.consumerdemo.dao;

import com.cuihui.consumer.consumerdemo.model.User;

import java.util.List;

//@Mapper //引导文件注解@MapperScan可替代
public interface IUserDao {
    public List<User> queryList();
}
