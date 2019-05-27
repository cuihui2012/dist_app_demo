package com.cuihui.provider.providerdemo.service.impl;

import com.cuihui.provider.providerdemo.dao.IUserDao;
import com.cuihui.provider.providerdemo.model.User;
import com.cuihui.provider.providerdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    //使用缓存,存入redis数据库,value::key组成redis数据库的key
    @Cacheable(value = "queryListCache",key = "'user.queryList'")
    public List<User> queryList() {
        System.out.println("执行去数据库查询的方法。");
        return userDao.queryList();
    }

    @Override
    @Transactional
    //删除对应列表缓存
    @CacheEvict(value = "queryListCache",key = "'user.queryList'")
    public String addUser(Map<String, String> paramMap) {
        String flag = "0";
        try{
            userDao.addUser(paramMap);
            flag = "1";
        } catch(SQLException e){
            e.printStackTrace();
        }
        return flag;
    }
}