package com.luntan.deppon.dao.member;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.member.MemberToken;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by cubc-luntan 2017/7/15.
 */
public interface IMemberTokenDao extends IBaseDao<MemberToken> {

    MemberToken getByToken(@Param("token") String token);

    Integer save(@Param("memberId") Integer memberId, @Param("token") String token, @Param("expireTime") Date expireTime);

}