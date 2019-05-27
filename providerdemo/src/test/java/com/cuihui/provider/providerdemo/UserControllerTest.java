package com.cuihui.provider.providerdemo;

import com.cuihui.provider.providerdemo.controller.UserController;
import com.cuihui.provider.providerdemo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * SpringBoot集成junit测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderdemoApplication.class) //属性用于指定引导类
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Test
    public void testQueryList(){
        List<User> list = userController.queryList();
        System.out.println("junit pubsub----->" + list);
    }
}
