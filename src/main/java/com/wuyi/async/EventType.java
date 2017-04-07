package com.wuyi.async;

/**
 * 列出动作
 * Created by wy on 2017/4/3.
 */
public enum  EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

     EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
