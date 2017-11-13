package com.luntan.deppon.web.manage;

import com.luntan.deppon.core.annotation.Before;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.DateFormatUtil;
import com.luntan.deppon.interceptor.AdminLoginInterceptor;
import com.luntan.deppon.model.common.Ads;
import com.luntan.deppon.service.common.IAdsService;
import com.luntan.deppon.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/9/07.
 */
@Controller
@RequestMapping("/${managePath}/ads")
@Before(AdminLoginInterceptor.class)
public class AdsController extends BaseController{
    private static final String MANAGE_FTL_PATH = "/manage/ads/";
    @Resource
    private IAdsService adsService;

    @RequestMapping("/list")
    public String list(Model model){
        Page page = new Page(request);
        ResponseModel responseModel = adsService.listByPage(page);
        model.addAttribute("model",responseModel);
        return MANAGE_FTL_PATH + "list";
    }

    @RequestMapping("/add")
    public String add(){
        return MANAGE_FTL_PATH + "add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(Ads ads){
        String startTimeStr = getParam("startDateTime");
        String endTimeStr = getParam("endDateTime");
        ads.setStartTime(DateFormatUtil.formatDateTime(startTimeStr));
        ads.setEndTime(DateFormatUtil.formatDateTime(endTimeStr));
        ads.setContent(ads.getContent().replace("&lt;","<").replace("&gt;",">").replace("&#47;","/"));
        ResponseModel responseModel = adsService.save(ads);
        if(responseModel.getCode() == 0){
            responseModel.setCode(3);
        }
        return responseModel;
    }


    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Ads ads = adsService.findById(id);
        model.addAttribute("ads",ads);
        return MANAGE_FTL_PATH + "edit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Ads ads){
        if (ads == null){
            return new ResponseModel(-1,"参数错误");
        }
        String startTimeStr = getParam("startDateTime");
        String endTimeStr = getParam("endDateTime");
        ads.setStartTime(DateFormatUtil.formatDateTime(startTimeStr));
        ads.setEndTime(DateFormatUtil.formatDateTime(endTimeStr));
        ads.setContent(ads.getContent().replace("&lt;","<").replace("&gt;",">").replace("&#47;","/"));
        ResponseModel responseModel = adsService.update(ads);
        if(responseModel.getCode() == 0){
            responseModel.setCode(3);
        }
        return responseModel;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id){
        return adsService.delete(id);
    }

    @RequestMapping("/enable/{id}")
    @ResponseBody
    public Object enable(@PathVariable("id") Integer id){
        return adsService.enable(id);
    }


}
