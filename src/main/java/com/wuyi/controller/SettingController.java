package com.wuyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wy on 2017/3/23.
 */
@Controller
public class SettingController {
    @RequestMapping("/setting/zhangsan")
    @ResponseBody
    public String setting(){
        return "setting";
    }

}
