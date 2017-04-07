package com.wuyi.service;


import com.wuyi.dao.NewsDao;
import com.wuyi.model.News;
import com.wuyi.util.ToutiaoUtil;
import main.java.com.UpYun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.UUID;

/**
 * Created by wy on 2017/3/25.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

    public String saveImage(MultipartFile file) throws IOException{
        // 创建实例
       // UpYun upyun = new UpYun("wuyi-admin","wu347446043","wu19911207");

// 可选属性1，是否开启 debug 模式，默认不开启
       // upyun.setDebug(false);
// 可选属性2，超时时间，默认 30s
        //upyun.setTimeout(30);
        int dotpos=file.getOriginalFilename().lastIndexOf(".");
        if(dotpos<0){
            return null;
        }
        String fileExt=file.getOriginalFilename().substring(dotpos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){
            return null;
        }

        String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        // 文件上传
        //upyun.setContentMD5(UpYun.md5(fileName));     // 计算文件 MD5，如果文件太大或计算不便，可以不计算


        //Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        //boolean result4 = upyun.writeFile("/"+fileName, file);
       // System.out.println(result4);
        return ToutiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;
    }
    public News getById(int newsId){
        return newsDao.getById(newsId);
    }

    public int updateCommentCount(int id,int count){
        return newsDao.updateCommentCount(id,count);
    }

    public int addNews(News news) {
        newsDao.addNews(news);
        return news.getId();
    }
}
