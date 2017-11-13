package com.luntan.deppon.web.manage;

import com.luntan.deppon.core.annotation.Before;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.interceptor.AdminLoginInterceptor;
import com.luntan.deppon.model.common.Picture;
import com.luntan.deppon.model.system.Action;
import com.luntan.deppon.model.system.ActionLog;
import com.luntan.deppon.service.common.IPictureService;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.service.system.IActionService;
import com.luntan.deppon.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2017/2/14.
 */
@Controller
@RequestMapping("/${managePath}/system/action/")
@Before(AdminLoginInterceptor.class)
public class ActionController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/system/action/";
    @Resource
    private IActionService actionService;
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private IPictureService pictureService;

    @RequestMapping("list")
    public String actionList(Model model){
        List<Action> list = actionService.list();
        model.addAttribute("list",list);
        return MANAGE_FTL_PATH + "list";
    }

    @RequestMapping("edit/{id}")
    public String find(@PathVariable("id") Integer id, Model model){
        Action action = actionService.findById(id);
        model.addAttribute("action",action);
        return MANAGE_FTL_PATH + "edit";
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(Action action){
        return actionService.update(action);
    }

    @RequestMapping(value = "isenable/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object isenable(@PathVariable("id") Integer id){
        return actionService.isenable(id);
    }

    @RequestMapping("actionLogList")
    public String actionLogList(@RequestParam(value = "memberId",required = false) Integer memberId, Model model){
        Page page = new Page(request);
        if(memberId == null){
            memberId = 0;
        }
        ResponseModel<ActionLog> list = actionLogService.listByPage(page,memberId);
        model.addAttribute("model",list);
        return MANAGE_FTL_PATH + "actionLogList";
    }

    @RequestMapping("memberActionLog")
    public String memberActionLog(@RequestParam(value = "memberId",required = false) Integer memberId, Model model){
        Page page = new Page(request);
        if(memberId == null){
            memberId = 0;
        }
        ResponseModel<ActionLog> list = actionLogService.memberActionLog(page,memberId);
        model.addAttribute("model",list);
        return MANAGE_FTL_PATH + "memberActionLog";
    }/**/

    /*/memberActionLog
    * 获取指定类型图片列表 上传图片  更新指定id图片地址  再查图片地址 回显
    * */
    @RequestMapping("manageBanner")
    public String manageBanner(Model model){
        Page page = new Page(request);
        ResponseModel<Picture> list =pictureService.findBanner(page);
        model.addAttribute("model",list);
        return MANAGE_FTL_PATH + "manageBanner";
    }


}
