package com.luntan.deppon.web.front;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.dao.member.IMemberFansDao;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.MemberFans;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.model.system.ActionLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private IMemberFansDao memberFansDao;

    //原有动态接口
//    @RequestMapping("list")
//    public String list(Model model){
//        Page page = new Page(request);
//        ResponseModel<ActionLog> actionList = actionLogService.memberActionLog(page,0);
//        model.addAttribute("model", actionList);
//        return jeesnsConfig.getFrontTemplate() + "/action/list";
//    }
    //关注动态接口
    @RequestMapping("list")
    public String list(Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        List<MemberFans> memberList=new ArrayList<>();
        if(loginMember==null){
            memberList=new ArrayList<>();
            MemberFans memberFans=new MemberFans();
            memberFans.setId(0);
            memberList.add(memberFans);
        }else {
            memberList = memberFansDao.followsList(page, loginMember.getId());
            if (memberList == null || memberList.size() == 0) {
                memberList = new ArrayList<>();
                MemberFans memberFans = new MemberFans();
                memberFans.setId(0);
                memberList.add(memberFans);
            }
        }
        List<Integer> list=new ArrayList<>();
        for(MemberFans item:memberList){
            list.add(item.getFollowWho());
        }
        Integer[] memberIds = list.toArray(new Integer[list.size()]);
        ResponseModel<ActionLog> actionList = actionLogService.memberActionLogFocus(page,memberIds);
        model.addAttribute("model", actionList);
        return jeesnsConfig.getFrontTemplate() + "/action/list";
    }


}
