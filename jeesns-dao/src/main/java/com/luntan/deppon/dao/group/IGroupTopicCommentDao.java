package com.luntan.deppon.dao.group;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.group.GroupTopicComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cubc-luntan 16/12/27.
 */
public interface IGroupTopicCommentDao extends IBaseDao<GroupTopicComment> {

    List<GroupTopicComment> listByGroupTopic(@Param("page") Page page, @Param("groupTopicId") Integer groupTopicId);

    int deleteByTopic(@Param("groupTopicId") Integer groupTopicId);
}