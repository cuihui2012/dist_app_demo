package com.cuihui.provider.providerdemo.service;

import com.cuihui.provider.providerdemo.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public List<User> queryList();
    public String addUser(Map<String,String> paramMap);
}
