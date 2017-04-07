package com.wuyi.model;

import org.springframework.stereotype.Component;

/**
 * Created by wy on 2017/3/26.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clean(){
        users.remove();
    }
}
