package com.luntan.deppon.dao.system;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.system.Action;
import org.apache.ibatis.annotations.Param;

/**
 * Created by cubc-luntan 2017/2/14.
 */
public interface IActionDao extends IBaseDao<Action> {
    int isenable(@Param("id") Integer id);
}
