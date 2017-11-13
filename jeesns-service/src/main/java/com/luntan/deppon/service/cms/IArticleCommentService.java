package com.luntan.deppon.service.cms;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.cms.ArticleComment;
import com.luntan.deppon.model.member.Member;


/**
 * Created by cubc-luntan 2016/10/14.
 */
public interface IArticleCommentService {

    ArticleComment findById(int id);

    ResponseModel save(Member loginMember, String content, Integer articleId);

    ResponseModel delete(Member loginMember, int id);

    ResponseModel listByArticle(Page page, int articleId);

    void deleteByArticle(Integer articleId);
}
