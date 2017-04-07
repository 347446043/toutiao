package com.wuyi.controller;

import com.wuyi.model.EntityType;
import com.wuyi.model.HostHolder;
import com.wuyi.service.LikeService;
import com.wuyi.service.NewsService;
import com.wuyi.util.ToutiaoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wy on 2017/4/2.
 */
@Controller
public class LikeController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @RequestMapping(path = {"/like"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(@Param(value = "newsId")int newsId){
        int useId = hostHolder.getUser().getId();
        long likeCount = likeService.like(useId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateCommentCount(newsId,(int)likeCount);
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String disLike(@Param(value = "newsId")int newsId){
        int useId = hostHolder.getUser().getId();
        long likeCount = likeService.disLike(useId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateCommentCount(newsId,(int)likeCount);
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
