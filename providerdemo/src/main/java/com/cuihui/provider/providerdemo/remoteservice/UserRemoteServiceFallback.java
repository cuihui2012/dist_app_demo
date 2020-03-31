package com.cuihui.provider.providerdemo.remoteservice;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRemoteServiceFallback implements IUserRemoteService {
    @Override
    public List<Object> queryList() {
        List<Object> list = new ArrayList<>();
        list.add("容错机制");
        return list;
    }
}
