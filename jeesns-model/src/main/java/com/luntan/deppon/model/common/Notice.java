package com.luntan.deppon.model.common;

import java.io.Serializable;

/**
 * @author 380853    mingruishen
 * @create 2018/1/27
 * jeesns
 */
public class Notice implements Serializable{

    private static final long serialVersionUID = 834675733643292834L;

    private Long id;
    private String operationType;
    private String toId;
    private Integer fromId;
    private String url;
    private String isRead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
