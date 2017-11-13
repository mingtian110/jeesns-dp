package com.luntan.deppon.service.member;


import com.luntan.deppon.model.member.MemberToken;

/**
 * Created by cubc-luntan 2017/7/15.
 */
public interface IMemberTokenService {

    MemberToken getByToken(String token);

    void save(Integer memberId,String token);

}