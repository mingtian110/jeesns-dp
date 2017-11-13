package com.luntan.deppon.dao.group;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.group.Group;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by cubc-luntan 16/12/23.
 */
public interface IGroupDao extends IBaseDao<Group> {

    /**
     * 获取群组
     * @return
     */
    List<Group> listByPage(@Param("page") Page page, @Param("status") Integer status, @Param("key") String key);

    /**
     * 修改状态
     *
     * @param id
     * @return
     */
    Integer changeStatus(@Param("id") Integer id);

    List<Group> listByCustom(@Param("status") int status, @Param("num") int num, @Param("sort") String sort);

}