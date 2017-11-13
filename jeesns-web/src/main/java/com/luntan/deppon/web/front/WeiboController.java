package com.luntan.deppon.web.front;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.Const;
import com.luntan.deppon.core.utils.ErrorUtil;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.dao.member.IMemberFansDao;
import com.luntan.deppon.model.member.MemberFans;
import com.luntan.deppon.service.weibo.IWeiboCommentService;
import com.luntan.deppon.service.weibo.impl.WeiboCommentServiceImpl;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.weibo.Weibo;
import com.luntan.deppon.service.weibo.IWeiboService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2016/12/20.
 */
@Controller("frontWeiboController")
@RequestMapping("/weibo")
public class WeiboController extends BaseController {
    @Resource
    private IWeiboService weiboService;
    @Resource
    private IWeiboCommentService weiboCommentService;
    @Resource
    private JeesnsConfig jeesnsConfig;
    @Resource
    private IMemberFansDao memberFansDao;

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    public Object publish(String content,String pictures,RedirectAttributes attr){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        ResponseModel responseModel = weiboService.save(request, loginMember,content, pictures);
        if (responseModel.getCode() >= 0){
            attr.addAttribute("code",responseModel.getCode());
            attr.addAttribute("message",responseModel.getMessage());
            return responseModel;
//            return "redirect:/list";
        }else {
            return responseModel;
        }
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "key",required = false,defaultValue = "") String key, Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResponseModel responseModel = weiboService.listByPage(page,0,loginMemberId,key);
        model.addAttribute("model",responseModel);
        List<Weibo> hotList = weiboService.hotList(loginMemberId);
        model.addAttribute("hotList",hotList);
        model.addAttribute("loginUser", loginMember);
        return jeesnsConfig.getFrontTemplate() + "/weibo/list";
    }
    @RequestMapping(value = "/mylist",method = RequestMethod.GET)
    public String mylist(@RequestParam(value = "key",required = false,defaultValue = "") String key, Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        //查询自己关注的人 select *  from fans where who_follow =#{id}
        List<MemberFans> list = memberFansDao.followsList(page, loginMemberId);
        //缩短条件

        ResponseModel responseModel = weiboService.listByPage(page,0,loginMemberId,key);
        model.addAttribute("model",responseModel);
        List<Weibo> hotList = weiboService.hotList(loginMemberId);
        model.addAttribute("hotList",hotList);
        model.addAttribute("loginUser", loginMember);
        return jeesnsConfig.getFrontTemplate() + "/weibo/list";
    }

    @RequestMapping(value = "/detail/{weiboId}",method = RequestMethod.GET)
    public String detail(@PathVariable("weiboId") Integer weiboId, Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Weibo weibo = weiboService.findById(weiboId,loginMemberId);
        if(weibo == null){
            model.addAttribute("msg", WeiboCommentServiceImpl.weiboAlias+"不存在");
            return jeesnsConfig.getFrontTemplate() + Const.INDEX_ERROR_FTL_PATH;
        }
        model.addAttribute("weibo",weibo);
        model.addAttribute("loginUser", loginMember);
        return jeesnsConfig.getFrontTemplate() + "/weibo/detail";
    }

    @RequestMapping(value="/delete/{weiboId}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("weiboId") Integer weiboId){
        Member loginMember = MemberUtil.getLoginMember(request);
        ResponseModel responseModel = weiboService.userDelete(request, loginMember,weiboId);
        if(responseModel.getCode() >= 0){
            responseModel.setCode(2);
            responseModel.setUrl(request.getContextPath() + "/weibo/list");
        }
        return responseModel;
    }


    @RequestMapping(value="/comment/{weiboId}",method = RequestMethod.POST)
    @ResponseBody
    public Object comment(@PathVariable("weiboId") Integer weiboId, String content, Integer weiboCommentId){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        return weiboCommentService.save(loginMember,content,weiboId,weiboCommentId);
    }

    @RequestMapping(value="/commentList/{weiboId}.json",method = RequestMethod.GET)
    @ResponseBody
    public Object commentList(@PathVariable("weiboId") Integer weiboId){
        Page page = new Page(request);
        if(weiboId == null){
            weiboId = 0;
        }
        return weiboCommentService.listByWeibo(page,weiboId);
    }

    @RequestMapping(value="/favor/{weiboId}",method = RequestMethod.GET)
    @ResponseBody
    public Object favor(@PathVariable("weiboId") Integer weiboId){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        if(weiboId == null) {
            return new ResponseModel(-1, "非法操作");
        }
        return weiboService.favor(loginMember,weiboId);
    }
}
