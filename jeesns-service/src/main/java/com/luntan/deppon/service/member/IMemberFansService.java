package com.luntan.deppon.service.member;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.MemberFans;


/**
 * Created by cubc-luntan 17/2/21.
 */
public interface IMemberFansService {

    ResponseModel save(Integer whoFollowId, Integer followWhoId);

    ResponseModel delete(Integer whoFollowId, Integer followWhoId);

    ResponseModel followsList(Page page, Integer whoFollowId);

    ResponseModel fansList(Page page, Integer followWhoId);

    MemberFans find(Integer whoFollowId, Integer followWhoId);
}
