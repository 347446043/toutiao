package com.wuyi.service;

import com.wuyi.util.ToutiaoUtil;
import main.java.com.UpYun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by wy on 2017/3/31.
 */
@Service
public class UpyunService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpyunService.class);
    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "wuyi-admin";
    private static final String OPERATOR_NAME = "wu347446043";
    private static final String OPERATOR_PWD = "wu19911207";

    /** 根目录 */
    private static final String DIR_ROOT = "/";
    /** 多级目录 */
   // private static final String DIR_MORE = "/uploadImage/";
    /** 目录名 */
    private static final String FOLDER_NAME = "image/";
    /** 上传到upyun的文件名 */
   // private static final String FILE_NAME = "7.jpg";

    /** 本地待上传的测试文件 */
   //private static final String SAMPLE_TXT_FILE = System
   //         .getProperty("user.dir") + "/7.jpg";

    private static UpYun upyun = null;

  /*  static {
        File txtFile = new File(SAMPLE_TXT_FILE);

        if (!txtFile.isFile()) {
            System.out.println("本地待上传的测试文件不存在！");
        }
    }*/
  public String saveImage(MultipartFile file) throws IOException{
      try {
          // 方法1：创建一级目录
          String dir1 = DIR_ROOT + FOLDER_NAME;

          //upyun.mkDir(dir1);
          upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
          upyun.setDebug(true);
          int dotpos=file.getOriginalFilename().lastIndexOf(".");
          if(dotpos<0){
              return null;
          }
          String fileExt=file.getOriginalFilename().substring(dotpos+1).toLowerCase();
          if(!ToutiaoUtil.isFileAllowed(fileExt)){
              return null;
          }
          String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
          String filePath = dir1 + fileName;
          boolean result = upyun.writeFile(filePath, file.getBytes(), true);
          System.out.println("3.上传 " + filePath + isSuccess(result));
          if(result) {
              return ToutiaoUtil.IMAGE_DIR + FOLDER_NAME + fileName;
          }else {
              LOGGER.error("又拍异常:" + result);
              return null;
          }
      } catch (Exception e) {
          // 请求失败时打印的异常的信息
          LOGGER.error("又拍云异常:" + e.getMessage());
          return null;
      }
  }
    private static String isSuccess(boolean result) {
        return result ? " 成功" : " 失败";
    }


}
