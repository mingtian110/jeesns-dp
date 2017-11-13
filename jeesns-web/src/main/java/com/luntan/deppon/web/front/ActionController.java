package com.luntan.deppon.web.front;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.model.system.ActionLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 动态
 * Created by cubc-luntan 2017/3/8.
 */
@Controller("frontActionController")
@RequestMapping("/action/")
public class ActionController extends BaseController {
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private JeesnsConfig jeesnsConfig;

    @RequestMapping("list")
    public String list(Model model){
        Page page = new Page(request);
        ResponseModel<ActionLog> actionList = actionLogService.memberActionLog(page,0);
        model.addAttribute("model", actionList);
        return jeesnsConfig.getFrontTemplate() + "/action/list";
    }


}
