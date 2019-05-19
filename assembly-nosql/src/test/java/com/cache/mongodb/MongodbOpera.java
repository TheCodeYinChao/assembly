package com.cache.mongodb;

import com.alibaba.fastjson.JSON;
import com.cache.mongodb.model.User;
import com.cache.mongodb.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MongodbOpera {
    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUser() {

        User user = new User();

        user.setId(new Random().nextLong());
        user.setPassWord("123456");
        user.setUserName("testAcount");
        String collectName = "demo";
        userRepository.saveUser(user,collectName);
    }

    @Test
    public void findUsere() {
        User userName = userRepository.findUserByUserName("testAcount");
        log.info("find result : {}",JSON.toJSONString(userName));
    }
}
