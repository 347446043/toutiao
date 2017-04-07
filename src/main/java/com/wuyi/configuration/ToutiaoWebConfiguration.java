package com.wuyi.configuration;

import com.wuyi.interceptor.LoginRequiredInterceptor;
import com.wuyi.interceptor.PassInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wy on 2017/3/26.
 * 注册拦截器
 */
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private PassInterceptor passInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/msg/*").addPathPatterns("/like").addPathPatterns("/dislike");
        super.addInterceptors(registry);
    }
}
