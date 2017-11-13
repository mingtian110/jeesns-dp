package com.luntan.deppon.directive;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.directive.BaseDirective;
import com.luntan.deppon.core.handler.DirectiveHandler;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.weibo.Weibo;
import com.luntan.deppon.service.weibo.IWeiboService;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by cubc-luntan 2017/5/18.
 */
@Component
public class WeiboDirective extends BaseDirective {
    @Resource
    private HttpServletRequest request;

    @Resource
    private IWeiboService weiboService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int day = handler.getInteger("day",0);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        List<Weibo> list = weiboService.listByCustom(loginMemberId,sort,num,day);
        handler.put("weiboList", list).render();
    }

}
