package com.wuyi;


import com.wuyi.dao.*;
import com.wuyi.model.*;
import com.wuyi.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDBTests {
	Random random=new Random();
	@Autowired
	private UserDao userDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private LoginTicketDao loginTicketDao;
	@Autowired
	private CommentDao commentDao;

	@Autowired
	private MessageService messageService;

	@Test
	public void contextLoads() {
		User user=new User();
		for(int i=0;i<10;i++){
			user.setName(String.format("USER%d",i));
			user.setPassword("123456");
			user.setSalt("");
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			userDao.addUser(user);
			userDao.updatePassword(user);

			News news=new News();
			news.setTitle(String.format("TITLE%d",i));
			news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
			news.setImage(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			news.setLikeCount(i+1);
			news.setCommentCount(i);
			Date date=new Date();
			date.setTime(date.getTime()+1000*3600*5*i);
			news.setCreatedDate(date);
			news.setUserId(i+1);
			newsDao.addNews(news);

			for (int j = 0; j < 3; ++j) {
				Comment comment = new Comment();
				comment.setUserId(i+1);
				comment.setEntityId(news.getId());
				comment.setEntityType(EntityType.ENTITY_NEWS);
				comment.setStatus(0);
				comment.setCreatedDate(new Date());
				comment.setContent("Comment " + String.valueOf(j));
				commentDao.addComment(comment);
			}

			LoginTicket loginTicket=new LoginTicket();
			loginTicket.setUserId(i+1);
			loginTicket.setExpired(date);
			loginTicket.setStatus(0);
			loginTicket.setTicket(String.format("TICKET%d",i+1));
			loginTicketDao.addTicket(loginTicket);

			loginTicketDao.updateStatus(loginTicket.getTicket(),2);


		}
		userDao.deleteById(1);
		Assert.assertNull(userDao.selectById(1));
		Assert.assertEquals(1, loginTicketDao.selectByTicket("TICKET1").getUserId());
		Assert.assertEquals(2, loginTicketDao.selectByTicket("TICKET1").getStatus());

		Assert.assertNotNull(commentDao.selectByEntity(1, EntityType.ENTITY_NEWS).get(0));

	}



}
