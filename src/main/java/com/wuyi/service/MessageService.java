package com.wuyi.service;

import com.wuyi.dao.MessageDao;
import com.wuyi.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wy on 2017/3/28.
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public int addMessage(Message message){
        return messageDao.addMessage(message);
    }

    public List<Message> getConversationDetail( String conversationId,
                                        int offset, int limit){
        return messageDao.getConversationDetail(conversationId,offset,limit);
    }
    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getConvesationUnreadCount(int userId, String conversationId) {
        return messageDao.getConvesationUnreadCount(userId, conversationId);
    }

    public int updateMessage(int id){
        return messageDao.updateMessage(id);
    }

}
