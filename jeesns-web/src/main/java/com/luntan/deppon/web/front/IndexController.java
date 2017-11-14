package com.luntan.deppon.web.front;

import com.luntan.deppon.service.cms.IArticleService;
import com.luntan.deppon.service.common.IArchiveService;
import com.luntan.deppon.service.common.ILinkService;
import com.luntan.deppon.service.group.IGroupFansService;
import com.luntan.deppon.service.member.IMemberFansService;
import com.luntan.deppon.service.member.IMemberService;
import com.luntan.deppon.service.member.IMessageService;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.common.utils.EmojiUtil;
import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.Const;
import com.luntan.deppon.core.utils.ErrorUtil;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.service.group.IGroupService;
import com.luntan.deppon.service.group.IGroupTopicService;
//import com.lxinet.jeesns.cms.service.IArticleService;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.system.ActionLog;
//import com.lxinet.jeesns.weibo.service.IWeiboService;
import com.luntan.deppon.service.weibo.IWeiboService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2016/11/25.
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController {
    @Resource
    private IArticleService articleService;
    @Resource
    private IGroupTopicService groupTopicService;
    @Resource
    private IGroupService groupService;
    @Resource
    private IWeiboService weiboService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IArchiveService archiveService;
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private JeesnsConfig jeesnsConfig;
    @Resource
    private IGroupFansService groupFansService;
    @Resource
    private IMemberFansService memberFansService;
    @Resource
    private ILinkService linkService;

    @RequestMapping(value={"/", "index"},method = RequestMethod.GET)
    public String index(@RequestParam(value = "key",required = false,defaultValue = "") String key, Integer cateid,Model model) {
        Page page = new Page(request);
        if(cateid == null){
            cateid = 0;
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResponseModel articleModel = articleService.listByPage(page,key,cateid,1,0);
        ResponseModel groupTopicModel = groupTopicService.listByPage(page,key,cateid,1,0);
        ResponseModel groupModel = groupService.listByPage(1,page,key);
        ResponseModel linkModel = linkService.recommentList();
        page.setPageSize(50);
        ResponseModel weiboModel = weiboService.listByPage(page,0,loginMemberId,"");
        model.addAttribute("articleModel",articleModel);
        model.addAttribute("groupTopicModel",groupTopicModel);
        model.addAttribute("groupModel",groupModel);
        model.addAttribute("weiboModel",weiboModel);
        model.addAttribute("linkModel",linkModel);

        return jeesnsConfig.getFrontTemplate() + "/index";
    }

    @Resource
    private IMessageService messageService;
    @RequestMapping(value = "u/{id}",method = RequestMethod.GET)
    public String u(@PathVariable("id") Integer id, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);
        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        ResponseModel<ActionLog> list = actionLogService.memberActionLog(page,id);
        model.addAttribute("actionLogModel",list);
//        int unReadMessageNum = messageService.countUnreadNum(loginMember.getId());
//        request.setAttribute("unReadMessageNum", unReadMessageNum);
        return jeesnsConfig.getFrontTemplate() + "/u";
    }

    @RequestMapping(value = "u/{id}/home/{type}",method = RequestMethod.GET)
    public String home(@PathVariable("id") Integer id, @PathVariable("type") String type, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);

        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        int loginMemberId = 0;
        if(loginMember != null){
            loginMemberId = loginMember.getId().intValue();
        }
        if("article".equals(type)){
            model.addAttribute("model", articleService.listByPage(page,"",0,1, id));
        } else if("groupTopic".equals(type)){
            model.addAttribute("model", groupTopicService.listByPage(page,"",0,1, id));
        } else if("group".equals(type)){
            model.addAttribute("model", groupFansService.listByMember(page, id));
        } else if("weibo".equals(type)){
            model.addAttribute("model", weiboService.listByPage(page,id,loginMemberId,""));
        } else if("follows".equals(type)){
            model.addAttribute("model", memberFansService.followsList(page,id));
        } else if("fans".equals(type)){
            model.addAttribute("model", memberFansService.fansList(page,id));
        }
        model.addAttribute("type",type);
        return jeesnsConfig.getFrontTemplate() + "/home";
    }


    @RequestMapping(value = "newVersion",method = RequestMethod.GET)
    @ResponseBody
    public String newVersion(@RequestParam("callback") String callback){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LAST_SYSTEM_VERSION",Const.LAST_SYSTEM_VERSION);
        jsonObject.put("LAST_SYSTEM_UPDATE_TIME", Const.LAST_SYSTEM_UPDATE_TIME);
        return callback + "(" + jsonObject.toString() + ")";
    }
    /**
     * 获取Emoji数据
     * @return
     */
    @RequestMapping(value="/emoji/emojiJsonData.json",method = RequestMethod.GET)
    @ResponseBody
    public Object emojiJsonData(){
        return EmojiUtil.emojiJson();
    }

    @RequestMapping(value="/404",method = RequestMethod.GET)
    public String jeesns404(){
        return jeesnsConfig.getFrontTemplate() + "/common/404";
    }

    @RequestMapping(value="/error",method = RequestMethod.GET)
    public String error(){
        return jeesnsConfig.getFrontTemplate() + "/common/error";
    }

    /**
     * 友情链接
     * @param model
     * @return
     */
    @RequestMapping(value={"/link"},method = RequestMethod.GET)
    public String link(Model model) {
        ResponseModel linkModel = linkService.allList();
        model.addAttribute("linkModel",linkModel);
        return jeesnsConfig.getFrontTemplate() + "/link";
    }
    /**
     * 友情链接
     * @param model
     * @return
     */
    @RequestMapping(value={"/ninja"},method = RequestMethod.GET)
    public String ninja(Model model) {
//        ResponseModel linkModel = linkService.allList();
//        model.addAttribute("linkModel",linkModel);
        return jeesnsConfig.getManagePath() + "/ninja/index";
    }
}
