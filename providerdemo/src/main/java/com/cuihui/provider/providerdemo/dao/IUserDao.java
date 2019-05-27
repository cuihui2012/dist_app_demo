package com.cuihui.provider.providerdemo.dao;

import com.cuihui.provider.providerdemo.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//@Mapper //引导文件注解@MapperScan可替代
public interface IUserDao {
    public List<User> queryList();
    public void addUser(Map<String,String> paramMap) throws SQLException;
}
