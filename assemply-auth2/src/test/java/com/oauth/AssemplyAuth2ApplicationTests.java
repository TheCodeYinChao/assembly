package com.oauth;

import com.oauth.dao.UserRepositoty;
import com.oauth.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssemplyAuth2ApplicationTests {
	@Autowired
	private UserRepositoty userRepositoty;

	@Test
	public void contextLoads() {
		User admin = userRepositoty.findByName("admin");
		System.out.println(admin);
	}

}
