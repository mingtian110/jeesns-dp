package com.luntan.deppon.common.utils;

/**
 * Created by cubc-luntan 2017/2/15.
 */
public enum ActionLogType {
    TEXT(0),ARTICLE(1),WEIBO(2),GROUP_TOPIC(4);
    private Integer value;

    ActionLogType(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
