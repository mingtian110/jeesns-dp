package com.luntan.deppon.dao.member;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.MemberFans;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cubc-luntan 2017/2/16.
 */
public interface IMemberFansDao extends IBaseDao<MemberFans> {

    List<MemberFans> followsList(@Param("page") Page page, @Param("whoFollowId") Integer whoFollowId);

    List<MemberFans> fansList(@Param("page") Page page, @Param("followWhoId") Integer followWhoId);

    MemberFans find(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);

    Integer save(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);

    Integer delete(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);
}