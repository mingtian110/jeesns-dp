package com.luntan.deppon.web.manage;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.annotation.Before;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.interceptor.AdminLoginInterceptor;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.service.weibo.IWeiboService;
import com.luntan.deppon.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2016/11/22.
 */
@Controller("mamageWeiboController")
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class WeiboController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/weibo/";
    @Resource
    private IWeiboService weiboService;

    @RequestMapping("${managePath}/weibo/index")
    @Before(AdminLoginInterceptor.class)
    public String index(@RequestParam(value = "key",required = false,defaultValue = "") String key, Model model) {
        Page page = new Page(request);
        ResponseModel responseModel = weiboService.listByPage(page,0,0,key);
        model.addAttribute("model",responseModel);
        return MANAGE_FTL_PATH + "index";
    }

    @RequestMapping(value = "${managePath}/weibo/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("id") int id) {
        Member loginMember = MemberUtil.getLoginMember(request);
        return weiboService.delete(request, loginMember,id);
    }
}
