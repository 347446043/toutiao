package com.wuyi.controller;

import com.wuyi.aspect.LogAspect;
import com.wuyi.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by wy on 2017/3/21.
 */
@Controller
public class IndexController {
    @Autowired
    private ToutiaoService toutiaoService;
    private static final Logger LOGGER= LoggerFactory.getLogger(IndexController.class);
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        LOGGER.info("中间打印");
        return "hello world";
    }
    @RequestMapping("/profix/{groupId}/{userId}")
    @ResponseBody
    public String profix(@PathVariable("groupId")String groupId,
                         @PathVariable("userId")String userId, @RequestParam(value = "type",defaultValue = "1")int type){
        return String.format("{%s},{%s},{%d}",groupId,userId,type);

    }
    @RequestMapping("/vm")
    public String news(Model model){
        model.addAttribute("wi","wuyi");
        return "news";
    }
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headName=request.getHeaderNames();
        while (headName.hasMoreElements()){
            String name=headName.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        return sb.toString();
    }
    @RequestMapping("/ioc")
    @ResponseBody
    public String ioc(){
        return toutiaoService.say();

    }
}
