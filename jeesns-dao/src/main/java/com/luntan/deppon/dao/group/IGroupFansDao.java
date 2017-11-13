package com.luntan.deppon.dao.group;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.group.Group;
import com.luntan.deppon.model.group.GroupFans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cubc-luntan 16/12/26.
 */
public interface IGroupFansDao extends IBaseDao<GroupFans> {

    /**
     * 获取群组粉丝
     * @return
     */
    List<GroupFans> listByPage(@Param("page") Page page, @Param("groupId") Integer groupId);

    GroupFans findByMemberAndGroup(@Param("groupId") Integer groupId,@Param("memberId") Integer memberId);

    int save(@Param("groupId") Integer groupId,@Param("memberId") Integer memberId);

    int delete(@Param("groupId") Integer groupId,@Param("memberId") Integer memberId);


    /**
     * 获取用户关注的群组列表
     * @param page
     * @param memberId
     * @return
     */
    List<Group> listByMember(@Param("page") Page page, @Param("memberId") Integer memberId);
}