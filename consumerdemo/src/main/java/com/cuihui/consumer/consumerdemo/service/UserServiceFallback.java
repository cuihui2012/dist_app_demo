package com.cuihui.consumer.consumerdemo.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceFallback implements IUserService {
    @Override
    public List<Object> queryList() {
        List<Object> list = new ArrayList<>();
        list.add("容错机制");
        return list;
    }
}
