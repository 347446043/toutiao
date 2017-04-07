package com.wuyi.async;

import com.alibaba.fastjson.JSONObject;
import com.wuyi.util.JedisAdapter;
import com.wuyi.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 异步事件的入口
 * Created by wy on 2017/4/3.
 */
@Service
public class EventProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    private JedisAdapter jedisAdapter;
    //把事件放到异步队列中
    public boolean fireEvent(EventModel model){
      try{
          String json = JSONObject.toJSONString(model);
          String key = RedisKeyUtil.getEventQueueKey();
          jedisAdapter.lpush(key,json);
          //jedisAdapter.setObject(key,model);
          return true;
      }catch (Exception e){
          LOGGER.error("操作异常"+e.getMessage());
        return false;
      }
    }
}
