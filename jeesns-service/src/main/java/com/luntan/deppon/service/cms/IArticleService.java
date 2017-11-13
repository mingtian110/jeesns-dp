package com.luntan.deppon.service.cms;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.cms.Article;
import com.luntan.deppon.model.member.Member;

import java.util.List;


/**
 * Created by cubc-luntan 2016/10/14.
 */
public interface IArticleService {

    Article findById(int id);

    Article findById(int id,Member loginMember);

    ResponseModel save(Member member,Article article);

    ResponseModel update(Member member,Article article);

    ResponseModel delete(Member member,int id);

    ResponseModel listByPage(Page page, String key, int cateid, int status, int memberId);

    void updateViewCount(int id);

    ResponseModel audit(int id);

    ResponseModel favor(Member loginMember, int articleId);

    List<Article> listByCustom(int cid,String sort,int num,int day,int thumbnail);
}
