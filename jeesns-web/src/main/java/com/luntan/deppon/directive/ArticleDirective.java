package com.luntan.deppon.directive;

import com.luntan.deppon.model.cms.Article;
import com.luntan.deppon.service.cms.IArticleService;
import com.luntan.deppon.core.directive.BaseDirective;
import com.luntan.deppon.core.handler.DirectiveHandler;
import freemarker.template.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by cubc-luntan 2017/5/18.
 */
@Component
public class ArticleDirective extends BaseDirective {
    @Resource
    private IArticleService articleService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int cid = handler.getInteger("cid",0);
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int day = handler.getInteger("day",0);
        int thumbnail = handler.getInteger("thumbnail",0);
        List<Article> list = articleService.listByCustom(cid,sort,num,day,thumbnail);
        handler.put("articleList", list).render();
    }

}
