package com.wuyi.controller;

import com.wuyi.model.EntityType;
import com.wuyi.model.HostHolder;
import com.wuyi.model.News;
import com.wuyi.model.ViewObject;
import com.wuyi.service.LikeService;
import com.wuyi.service.NewsService;
import com.wuyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/3/24.
 */
@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    private List<ViewObject> getNews(int userId, int offset, int limit){
        int localUserId =  hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<News> newsList=newsService.getLatestNews(userId,offset,limit);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(News news : newsList){
            ViewObject vo=new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUser(news.getUserId()));
            if(localUserId!= 0){
               vo.set("like",likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS,news.getId()));
            }else{
                vo.set("like",0);
            }
            vos.add(vo);
        }

        return vos;
    }
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value="pop",defaultValue = "0")int pop){
        model.addAttribute("vos",getNews(0,0,10));
        model.addAttribute("pop",pop);
        return "home";
    }
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home1";
    }
}
