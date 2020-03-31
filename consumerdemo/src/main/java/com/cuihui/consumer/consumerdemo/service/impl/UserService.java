package com.cuihui.consumer.consumerdemo.service.impl;

import com.cuihui.consumer.consumerdemo.dao.IUserDao;
import com.cuihui.consumer.consumerdemo.model.User;
import com.cuihui.consumer.consumerdemo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> queryList() {
        logger.info("执行去数据库查询的方法。");
        return userDao.queryList();
    }
}