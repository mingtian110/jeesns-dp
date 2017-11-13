package com.luntan.deppon.dao.cms;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.cms.ArticleComment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章评论DAO接口
 * Created by cubc-luntan 2016/11/26.
 */
public interface IArticleCommentDao extends IBaseDao<ArticleComment> {

    List<ArticleComment> listByArticle(@Param("page") Page page, @Param("articleId") Integer articleId);

    int deleteByArticle(@Param("articleId") Integer articleId);
}