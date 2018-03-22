package com.luntan.deppon.service.member;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.MemberFans;


/**
 * Created by cubc-luntan 17/2/21.
 */
public interface IMemberNoticeService {

    ResponseModel noticeList(Page page, Integer followWhoId);

    Integer countNotice(Integer followWhoId);

}
