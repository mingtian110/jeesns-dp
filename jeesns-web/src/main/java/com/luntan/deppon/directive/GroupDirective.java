package com.luntan.deppon.directive;

import com.luntan.deppon.core.directive.BaseDirective;
import com.luntan.deppon.core.handler.DirectiveHandler;
import com.luntan.deppon.model.group.Group;
import com.luntan.deppon.service.group.IGroupService;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by cubc-luntan 2017/5/18.
 */
@Component
public class GroupDirective extends BaseDirective {
    @Resource
    private IGroupService groupService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int status = handler.getInteger("status",-1);
        List<Group> list = groupService.listByCustom(status,num,sort);
        handler.put("groupList", list).render();
    }

}
