package com.luntan.deppon.dao.common;

import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.common.Link;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by cubc-luntan 2017-10-13.
 */
public interface ILinkDao extends IBaseDao<Link>{

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<Link> listByPage(@Param("page") Page page);

    List<Link> recommentList();

    int enable(@Param("id") Integer id);
}
