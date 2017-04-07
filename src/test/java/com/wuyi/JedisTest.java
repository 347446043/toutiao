package com.wuyi;


import com.wuyi.model.User;
import com.wuyi.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class JedisTest {
	@Autowired
	private JedisAdapter jedisAdapter;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setName("zhangsan");
		user.setPassword("1");
		user.setHeadUrl("http://www");
		user.setSalt("123");

		jedisAdapter.setObject("user",user);

		User u = jedisAdapter.getObject("user",User.class);

		System.out.println(ToStringBuilder.reflectionToString(u));
	}
}
