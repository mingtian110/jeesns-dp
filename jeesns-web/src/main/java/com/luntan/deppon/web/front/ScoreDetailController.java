package com.luntan.deppon.web.front;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.interceptor.UserLoginInterceptor;
import com.luntan.deppon.core.annotation.Before;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.ScoreDetail;
import com.luntan.deppon.service.member.IScoreDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/4/7.
 */
@Controller("scoreDetailFrontController")
@RequestMapping("/member/scoreDetail")
@Before(UserLoginInterceptor.class)
public class ScoreDetailController extends BaseController {
    private static final String INDEX_FTL_PATH = "/member/scoreDetail/";
    @Resource
    private IScoreDetailService scoreDetailService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        Page page = new Page(request);
        ResponseModel<ScoreDetail> responseModel = scoreDetailService.list(page,loginMember.getId());
        model.addAttribute("model",responseModel);
        return INDEX_FTL_PATH + "list";
    }
}
