package com.wuyi.controller;

import com.wuyi.model.*;
import com.wuyi.service.*;
import com.wuyi.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wy on 2017/3/27.
 */
@Controller
public class NewsController {

    private static final Logger LOGGER= LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UpyunService upyunService;

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable(value = "newsId") int newsId, Model model){
        News news = newsService.getById(newsId);

        if(news!=null){
            List<Comment> comments = commentService.getCommentsByEntity(newsId , EntityType.ENTITY_NEWS);
            List<ViewObject> commentVos = new ArrayList<ViewObject>();
            for(Comment comment:comments){
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVos.add(vo);
            }
            model.addAttribute("comments",commentVos);
        }
        model.addAttribute("news" , news);
        model.addAttribute("owner",userService.getUser(news.getUserId()));
        return "detail";

    }
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam(value = "newsId")int newsId,@RequestParam(value = "content")String content){
        try {
            content = HtmlUtils.htmlEscape(content);

            Comment comment=new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setStatus(0);

            commentService.addComment(comment);

            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            newsService.updateCommentCount(newsId,count);

        }catch (Exception e){
            LOGGER.error("评论异常"+e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);

    }

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void image(@RequestParam(value = "name") String fileName, HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");
           //upyunService.testReadFile(fileName);
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR + fileName)), response.getOutputStream());
        }catch (Exception e){
            LOGGER.error("图片显示失败"+e.getMessage());
            ToutiaoUtil.getJSONString(1,"图片显示失败");
        }
    }
    @RequestMapping(path = {"/uploadImage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file")MultipartFile file){
        try{
                String fileUrl = upyunService.saveImage(file);
                if(fileUrl==null){
                    return ToutiaoUtil.getJSONString(1,"上传失败");
                }
                return ToutiaoUtil.getJSONString(0,fileUrl);

        }catch (Exception e){
            LOGGER.error("上传图片失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传失败");
        }
    }
    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        try {
            News news = new News();
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                // 设置一个匿名用户
                news.setUserId(3);
            }
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);
        } catch (Exception e) {
            LOGGER.error("添加资讯失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "发布失败");
        }
    }

}
