package com.luntan.deppon.dao.system;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.system.Config;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息DAO接口
 * Created by cubc-luntan 2016/11/26.
 */

public interface IConfigDao extends IBaseDao<Config> {

    boolean update(@Param("key") String key,@Param("value") String value);

    String getValue(@Param("key") String key);
}