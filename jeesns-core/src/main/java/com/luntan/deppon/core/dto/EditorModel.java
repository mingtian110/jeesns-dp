package com.luntan.deppon.core.dto;

import com.luntan.deppon.core.model.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cubc-luntan 2016/10/16.
 */
public class EditorModel<T> implements Serializable{
    private String errno;
    private List<String> data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
