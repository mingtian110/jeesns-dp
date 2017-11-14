package com.luntan.deppon.web.front;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.module.zookeeper.server.service.impl.ZooKeeperConfig;
import com.luntan.deppon.common.utils.ActionUtil;
import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.common.utils.ScoreRuleConsts;
import com.luntan.deppon.core.utils.*;
import com.luntan.deppon.core.utils.StringUtils;
import com.luntan.deppon.dao.member.IMemberDao;
import com.luntan.deppon.interceptor.AdminLoginInterceptor;
import com.luntan.deppon.interceptor.UserLoginInterceptor;
import com.luntan.deppon.core.annotation.Before;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.service.member.IMemberService;
import com.luntan.deppon.service.member.IMessageService;
import com.luntan.deppon.service.member.IScoreDetailService;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.core.utils.*;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.system.ActionLog;
import com.luntan.deppon.service.system.IConfigService;
//import com.lxinet.jeesns.modules.weibo.service.IWeiboService;
import com.luntan.deppon.common.utils.ConfigUtil;
import org.apache.commons.lang3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cubc-luntan 2016/11/22.
 */
@Controller("memberIndexController")
@RequestMapping("/member")
public class MemberController extends BaseController {
    private static final String MEMBER_FTL_PATH = "/member/";
    @Resource
    private IMemberService memberService;
    @Resource
    private IConfigService configService;
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private IMessageService messageService;
    @Resource
    private JeesnsConfig jeesnsConfig;
    @Autowired(required = false)
    private IMemberDao memberDao;
    @Autowired(required = false)
    private IScoreDetailService scoreDetailService;
    private final static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired(required = false)
    private  RestTemplate restTemplate;
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model,@RequestParam(value = "redirectUrl",required = false,defaultValue = "") String redirectUrl){
        logger.error("CUBC_PATH:"+url);
        Member loginMember = MemberUtil.getLoginMember(request);
        String jeedata = request.getParameter("jeedata");
        logger.error("jeedata:"+jeedata);
        if(StringUtils.isBlank(jeedata)){
            loginMember=MemberUtil.getLoginMember(request);
            if(loginMember != null){
                return "redirect:/member/";
            }
            model.addAttribute("redirectUrl",redirectUrl);
            return MEMBER_FTL_PATH + "/login";
        }
        String[] split = jeedata.split(" ");
        String id = split[0];
        String code = split[1];
        String name = split[2];
        String password=split[3];
        String deptName=split[4];
        String forObject = (String) restTemplate.getForObject(url+"/cubc/getjeeCodeById?id=" + id, String.class);
        logger.error("return value:"+forObject);
        logger.error("code:"+code);
        if(!forObject.equals(code)){
            model.addAttribute("redirectUrl",redirectUrl);
            return MEMBER_FTL_PATH + "/login";
        }
        Member member=new Member();
        member.setName(name/*+"-"+deptName*/);
        member.setPassword(password);
        ResponseModel responseModel = memberService.loginForCubc(member, request);
        loginMember=MemberUtil.getLoginMember(request);
        logger.error("loginMember:"+ JSONObject.toJSONString(loginMember));
        if(loginMember != null){
            return "redirect:/member/";
        }
        model.addAttribute("redirectUrl",redirectUrl);
        return MEMBER_FTL_PATH + "/login";
    }
    /**
     * 单点登录
     * @param member
     * @param repassword
     * @return
     */
    /**
     * Login-判断单点(dubbo/GET)-find-register-login
     */
    @RequestMapping(value = "/fromCubc",method = RequestMethod.POST)
    @ResponseBody
    public Object fromCubc(HttpServletRequest request){
        String sid = request.getParameter("jeedata");
        String[] split = sid.split(" ");
        String id=split[0];
        String code=split[1];
        String name=split[2];
        String password=split[3];
        String deptName=split[4];
        String phone=split[5];
        String position=split[6];
        String deptCode=split[7];
        Map<String, String> config = configService.getConfigToMap();
        if ("0".equals(config.get(ConfigUtil.MEMBER_REGISTER_OPEN))) {
            return new ResponseModel(-1, "注册功能已关闭");
        }
        String forObject =(String) restTemplate.getForObject(url+"/cubc/getjeeCodeById?id=" + id,String.class);
        String data =forObject;
        if(!data.equals(code)){
            return new ResponseModel(-1, "错误");
        }
        String afterUserName=name/*+"-"+deptName*/;
        String afterEmail=code+"@deppon.com";
        //登录内容:工号-姓名-密码(需解密)-邮箱:工号@deppon.com
        Member member=new Member();
        member.setName(afterUserName);
        member.setPassword(password);
        member.setEmail(afterEmail);
        member.setIntroduce(code);
        member.setPhone(phone);
        member.setPositions(position);
        member.setDeptCode(deptCode);
        member.setDeptName(deptName);
        member.setCode(code);
        /**
         * 查询是否已存在
         */
        if(memberDao.findByName(afterUserName) == null){//未注册
            member.setRegip(IpUtil.getIpAddress(request));
            member.setPassword(Md5Util.getMD5Code(member.getPassword()));
            member.setAvatar(Const.DEFAULT_AVATAR);
            if (memberDao.register(member) == 1) {
                actionLogService.save(member.getRegip(), member.getId(), ActionUtil.MEMBER_REG);
                //注册奖励
                scoreDetailService.scoreBonus(member.getId(), ScoreRuleConsts.REG_INIT);
                return new ResponseModel(2, "注册成功", request.getServletContext().getContextPath() + "/member/login");
            }
            return new ResponseModel(-1, "注册失败");
        }
        /**
         * 已注册  请求获取cubc工号  直接登录
         */
        ResponseModel responseModel = memberService.loginForCubc(member, request);
        String redirectUrl="";
        if (StringUtils.isNotEmpty(redirectUrl) && responseModel.getCode() >= 0){
            responseModel.setCode(3);
            responseModel.setUrl(redirectUrl);
        }
        responseModel.setMessage(request.getSession().getId());
//        return responseModel;
        return new ResponseModel(2, "跳转成功", request.getServletContext().getContextPath() + "/member/login");
    }
    @RequestMapping(value = "/loginFor",method = RequestMethod.GET)
    public String loginFor(Model model,@RequestParam(value = "redirectUrl",required = false,defaultValue = "") String redirectUrl){
            return "redirect:/member/";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Member> login(Member member,@RequestParam(value = "redirectUrl",required = false,defaultValue = "") String redirectUrl){

        String jeename = request.getParameter("jeedata");
        if(!StringUtils.isBlank(jeename)) {
            String[] split = jeename.split("\\:");
            Member findMember = (Member) request.getServletContext().getAttribute(split[0]+"-"+split[1]);
            MemberUtil.setLoginMember(request, findMember);
        }
        ResponseModel responseModel = memberService.login(member,request);
        if (StringUtils.isNotEmpty(redirectUrl) && responseModel.getCode() >= 0){
            responseModel.setCode(3);
            responseModel.setUrl(redirectUrl);
        }
        return responseModel;
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember != null){
            return "redirect:/member/";
        }
        return MEMBER_FTL_PATH + "/register";
    }

    static String url="";
    static {
        try {
            url=ZooKeeperConfig.getInstance().getConfigInfo("cubcweb.jeesns.url");
        }catch (Exception e){
        }
    }



    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel register(Member member,String repassword){
        Map<String,String> config = configService.getConfigToMap();
        if("0".equals(config.get(ConfigUtil.MEMBER_REGISTER_OPEN))){
            return new ResponseModel(-1,"注册功能已关闭");
        }
        if(member == null){
            return new ResponseModel(-1,"参数错误");
        }
        if(member.getName().length() < 6){
            return new ResponseModel(-1,"用户名长度最少6位");
        }
        if(!StringUtils.checkNickname(member.getName())){
            return new ResponseModel(-1,"用户名只能由中文、字母、数字、下划线(_)或者短横线(-)组成");
        }
        if(!StringUtils.isEmail(member.getEmail())){
            return new ResponseModel(-1,"邮箱格式错误");
        }
        if(member.getPassword().length() < 6){
            return new ResponseModel(-1,"密码长度最少6位");
        }
        if(!member.getPassword().equals(repassword)){
            return new ResponseModel(-1,"两次密码输入不一致");
        }
        return memberService.register(member,request);
    }

    @RequestMapping(value = "/active",method = RequestMethod.GET)
    public String active(){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return "redirect:/member/login";
        }
        return MEMBER_FTL_PATH + "/active";
    }

    @RequestMapping(value = "/active",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel active(String randomCode){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        return memberService.active(loginMember,randomCode,request);
    }

    @RequestMapping(value = "/sendEmailActiveValidCode",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel sendEmailActiveValidCode(){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        return memberService.sendEmailActiveValidCode(loginMember, request);
    }

    @RequestMapping(value = "/forgetpwd",method = RequestMethod.GET)
    public String forgetpwd(){
        return MEMBER_FTL_PATH + "/forgetpwd";
    }

    @RequestMapping(value = "/forgetpwd",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel forgetpwd(String name,String email){
        return memberService.forgetpwd(name, email, request);
    }

    @RequestMapping(value = "/resetpwd",method = RequestMethod.GET)
    public String resetpwd(String email,String token,Model model){
        model.addAttribute("email",email);
        model.addAttribute("token",token);
        return MEMBER_FTL_PATH + "/resetpwd";
    }

    @RequestMapping(value = "/resetpwd",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel resetpwd(String email,String token,String password,String repassword){
        if(StringUtils.isEmpty(password)){
            return new ResponseModel(-1,"新密码不能为空");
        }
        if(password.length() < 6){
            return new ResponseModel(-1,"密码不能少于6个字符");
        }
        if(!password.equals(repassword)){
            return new ResponseModel(-1,"两次密码输入不一致");
        }
        return memberService.resetpwd(email,token,password,request);
    }


    @RequestMapping(value = "/",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String index(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Page page = new Page(request);
        ResponseModel<ActionLog> list = actionLogService.memberActionLog(page,loginMemberId);
        model.addAttribute("actionLogModel",list);
//        int unReadMessageNum = messageService.countUnreadNum(loginMember.getId());
//        request.setAttribute("unReadMessageNum", unReadMessageNum);
//        model.addAttribute("unReadMessageNum",unReadMessageNum);
        return MEMBER_FTL_PATH + "index";
    }


    @RequestMapping(value = "/editInfo",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String editInfo(){
        return MEMBER_FTL_PATH + "editInfo";
    }

    @RequestMapping(value = "/editBaseInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel editBaseInfo(String name,String sex,String introduce){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.editBaseInfo(loginMember,name,sex,introduce);
    }

    @RequestMapping(value = "/editOtherInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel editOtherInfo(String birthday,String qq,String wechat,String contactPhone,
                                       String contactEmail,String website){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.editOtherInfo(loginMember,birthday,qq,wechat,contactPhone,contactEmail,website);
    }

    @RequestMapping(value = "/avatar",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String avatar(){
        return MEMBER_FTL_PATH + "avatar";
    }

    @RequestMapping(value = "/password",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String password(){
        return MEMBER_FTL_PATH + "password";
    }

    @RequestMapping(value = "/password",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel password(String oldPassword, String newPassword, String renewPassword){
        if(StringUtils.isEmpty(oldPassword)){
            return new ResponseModel(-1,"旧密码不能为空");
        }
        if(StringUtils.isEmpty(newPassword)){
            return new ResponseModel(-1,"新密码不能为空");
        }
        if(!newPassword.equals(renewPassword)){
            return new ResponseModel(-1,"两次密码输入不一致");
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.changepwd(loginMember,oldPassword,newPassword);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String logout(){
        MemberUtil.setLoginMember(request,null);
        return "redirect:/member/login";
    }

    /**
     * 关注、取消关注
     * @param followWhoId
     * @return
     */
    @RequestMapping(value = "/follows/{followWhoId}",method = RequestMethod.GET)
    @ResponseBody
    public Object follows(@PathVariable(value = "followWhoId") Integer followWhoId){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.follows(loginMember,followWhoId);
    }

    /**
     * 查询是否已关注该用户
     * @param followWhoId
     * @return
     */
    @RequestMapping(value = "/isFollowed/{followWhoId}",method = RequestMethod.GET)
    @ResponseBody
    public Object isFollowed(@PathVariable(value = "followWhoId") Integer followWhoId){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.isFollowed(loginMember,followWhoId);
    }


    /**
     * 获取该登录会员的收件信息
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String message(@RequestParam(value = "mid",required = false,defaultValue = "-1") Integer memberId,Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        Integer loginMemberId = -1;
        if(loginMember != null){
            loginMemberId = loginMember.getId();
        }
        //获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, loginMemberId);
//        获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, memberId, loginMemberId);
//        model.addAttribute("model", contactMembers);
        return MEMBER_FTL_PATH + "message";
    }
    /**
     * 轮询获取私信数
     */
    @RequestMapping(value = "/messageNum",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public void messageNum(@RequestParam(value = "mid",required = false,defaultValue = "-1") Integer memberId,Model model){
        int unReadMessageNum = messageService.countUnreadNum(memberId);
        request.setAttribute("unReadMessageNum", unReadMessageNum);
//        Page page = new Page(request);
//        Member loginMember = MemberUtil.getLoginMember(request);
//        Integer loginMemberId = -1;
//        if(loginMember != null){
//            loginMemberId = loginMember.getId();
//        }
        //获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, loginMemberId);
//        获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, memberId, loginMemberId);
//        model.addAttribute("model", contactMembers);
//        return MEMBER_FTL_PATH + "message";
    }

    /**
     * 获取联系人
     * @return
     */
    @RequestMapping(value = "/listContactMembers",method = RequestMethod.GET)
    @ResponseBody
    public Object listContactMembers(){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        if (loginMember != null){
            //获取联系人
            ResponseModel contactMembers = memberService.listContactMembers(page, loginMember.getId());
            return contactMembers;
        }
        return null;
    }

    /**
     * 获取聊天记录
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/messageRecords/{memberId}",method = RequestMethod.GET)
    @ResponseBody
    public Object messageRecords(@PathVariable("memberId") Integer memberId){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        if (loginMember != null){
            //获取聊天记录
            ResponseModel messageRecords = messageService.messageRecords(page, memberId, loginMember.getId());
            return messageRecords;
        }
        return null;
    }

    /**
     * 发送信息窗口
     * @param memberId
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendMessageBox",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String sendMessageBox(@RequestParam(value = "mid") Integer memberId,Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model, -1008, Const.INDEX_ERROR_FTL_PATH);
        }
        if(memberId == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model, -1000, Const.INDEX_ERROR_FTL_PATH);
        }
        Member findMember= memberService.findById(memberId);
        if(findMember == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model, -1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member", findMember);
        return MEMBER_FTL_PATH + "sendMessageBox";
    }

    /**
     * 发送信息
     * @param content
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/sendMessage",method = RequestMethod.POST)
    @ResponseBody
    public Object sendMessage(String content,Integer memberId){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        if(memberId == null){
            return new ResponseModel(-1,"请选择发送对象");
        }
        Member findMember= memberService.findById(memberId);
        if(findMember == null){
            return new ResponseModel(-1,"会员不存在");
        }
        return messageService.save(loginMember.getId(), memberId, content);
    }
    /**
     * 发送信息
     * @param memberName
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/sendMsg",method = RequestMethod.POST)
    @ResponseBody
    public Object sendMsg(Integer memberId,String memberName){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember == null){
            return new ResponseModel(-1,"请先登录");
        }
        if(memberId == null){
            return new ResponseModel(-1,"请选择发送对象");
        }
        Member findMember= memberService.findById(memberId);
        if(findMember == null){
            return new ResponseModel(-1,"会员不存在");
        }
        //当天发送量
        HashMap<String,Integer> msgCount = (HashMap<String,Integer> )request.getSession().getAttribute("msgCount");
        Date date=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String format = df.format(date);
        if(msgCount==null){//第一次发送
            msgCount=new HashMap<>();
            //天-量
            msgCount.put(format,1);
            request.getSession().setAttribute("msgCount",msgCount);
        }else{
            Integer count = msgCount.get(format);
            if(count==null){
                msgCount.clear();
                msgCount.put(format,1);
            }else{//判断发送是否过量
                count++;
                if(count>3) {
                    return new ResponseModel(-1, "今天发送提醒短信已达限值");
                }
                msgCount.put(format,count);
            }
            request.getSession().setAttribute("msgCount",msgCount);
        }
        return messageService.sendMsg(loginMember, findMember);
    }
    /**
     *找人
     */
    @RequestMapping("search")
    public String list(Model model){
        Page page = new Page(request);
        ResponseModel<Member> actionList = memberService.findSomeBody(page);
        model.addAttribute("model", actionList);
        return MEMBER_FTL_PATH + "search";
//        return jeesnsConfig.getMemberTemplate() + "/search";
    }

    @RequestMapping("searchByName")
    public String searchByName(String key,Model model) {
        Page page = new Page(request);
        ResponseModel responseModel = memberService.listByPage(page,key);
        model.addAttribute("model",responseModel);
        model.addAttribute("key",key);
        return MEMBER_FTL_PATH + "search";
    }

}
