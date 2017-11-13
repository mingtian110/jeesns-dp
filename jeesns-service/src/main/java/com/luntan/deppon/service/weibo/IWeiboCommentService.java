package com.luntan.deppon.service.weibo;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.weibo.WeiboComment;


/**
 * Created by cubc-luntan 2016/10/14.
 */
public interface IWeiboCommentService {

    WeiboComment findById(int id);

    ResponseModel save(Member loginMember, String content, Integer weiboId, Integer weiboCommentId);

    ResponseModel delete(Member loginMember,int id);

    ResponseModel listByWeibo(Page page, int articleId);

    void deleteByWeibo(Integer weiboId);
}
