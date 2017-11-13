package com.luntan.deppon.dao.common;

import com.luntan.deppon.model.common.Archive;
import org.apache.ibatis.annotations.Param;

/**
 * 文章DAO接口
 * Created by cubc-luntan 2016/9/26.
 */
public interface IArchiveDao extends IBaseDao<Archive> {

    Archive findByArchiveId(@Param("archiveId") Integer archiveId);

    /**
     * 更新阅读数
     * @param archiveId
     * @return
     */
    int updateViewCount(@Param("archiveId") int archiveId);

    int favor(@Param("archiveId") int archiveId, @Param("num") int num);
    
}