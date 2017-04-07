package com.wuyi.async.handler;

import com.wuyi.async.EventHandler;
import com.wuyi.async.EventModel;
import com.wuyi.async.EventType;
import com.wuyi.model.Message;
import com.wuyi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 登陆异常的一个处理
 * Created by wy on 2017/4/3.
 */
@Component
public class LoginExercationHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
