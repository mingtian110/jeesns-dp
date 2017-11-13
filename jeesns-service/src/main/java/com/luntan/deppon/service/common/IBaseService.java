package com.luntan.deppon.service.common;

import java.util.List;

/**
 * Created by cubc-luntan 16/9/29.
 */
public interface IBaseService<T> {

    T get(Integer id);

    int save(T entity);

    int update(T entity);

    int delete(Integer id);

    List allList();
}
