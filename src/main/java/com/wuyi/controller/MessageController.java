package com.wuyi.controller;


import com.wuyi.model.HostHolder;
import com.wuyi.model.Message;
import com.wuyi.model.User;
import com.wuyi.model.ViewObject;
import com.wuyi.service.MessageService;
import com.wuyi.service.UserService;
import com.wuyi.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wy on 2017/3/28.
 */
@Controller
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"} , method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        try {
            int userId = hostHolder.getUser().getId();
            List<Message> messageList = messageService.getConversationList(userId,0,10);
            List<ViewObject> viewObjects = new ArrayList<ViewObject>();
            for(Message msg: messageList){
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == userId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                vo.set("unread", messageService.getConvesationUnreadCount(userId, msg.getConversationId()));
                viewObjects.add(vo);
            }
            model.addAttribute("conversations",viewObjects);

        } catch (Exception e) {
            LOGGER.error("站内信获取失败"+ e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"} , method = {RequestMethod.GET})
    public String conversationDetail(Model model,@Param(value = "conversationId") String conversationId){
        try{
                List<Message> conversationList = messageService.getConversationDetail(conversationId , 0, 10);
                List<ViewObject> viewObjects = new ArrayList<ViewObject>();
                for(Message msg : conversationList){
                    ViewObject vo = new ViewObject();
                   messageService.updateMessage(msg.getId());
                    vo.set("message",msg);
                    User user = userService.getUser(msg.getFromId());
                    if(user == null){
                        continue;
                    }
                    vo.set("headUrl",user.getHeadUrl());
                    vo.set("userId",user.getId());
                    viewObjects.add(vo);
                }
                model.addAttribute("messages" , viewObjects);

        }catch (Exception e){
            LOGGER.error("获取详情消息失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.GET})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message msg = new Message();
            msg.setContent(content);
            msg.setFromId(fromId);
            msg.setToId(toId);
            msg.setCreatedDate(new Date());
            msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(msg);
            return ToutiaoUtil.getJSONString(msg.getId());
        } catch (Exception e) {
            LOGGER.error("增加评论失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "插入评论失败");
        }
    }
}
