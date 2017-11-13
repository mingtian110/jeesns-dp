package com.luntan.deppon.service.weibo;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.weibo.Weibo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cubc-luntan 2016/11/25.
 */
public interface IWeiboService {

    Weibo findById(int id, int memberId);

    ResponseModel save(HttpServletRequest request, Member loginMember, String content, String pictures);

    ResponseModel<Weibo> listByPage(Page page, int memberId, int loginMemberId, String key);
    ResponseModel<Weibo> listByFansPage(Page page, int memberId, int loginMemberId, String key);

    ResponseModel delete(HttpServletRequest request, Member loginMember, int id);

    ResponseModel userDelete(HttpServletRequest request, Member loginMember, int id);

    List<Weibo> hotList(int loginMemberId);

    ResponseModel favor(Member loginMember, int weiboId);

    List<Weibo> listByCustom(int loginMemberId, String sort,int num,int day);
}
