package com.wuyi.async;

import java.util.List;

/**
 * 用于处理动作的接口
 * Created by wy on 2017/4/3.
 */
public interface EventHandler {
    //用于去做这个动作
    void doHandler(EventModel model);
    //出现这些type中的事件的处理
    List<EventType> getSupportEventTypes();
}
