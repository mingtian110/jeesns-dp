package com.luntan.deppon.web.manage;

import com.luntan.deppon.core.annotation.Before;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.interceptor.AdminLoginInterceptor;
import com.luntan.deppon.model.common.Link;
import com.luntan.deppon.service.common.ILinkService;
import com.luntan.deppon.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/10/13.
 */
@Controller
@RequestMapping("/${managePath}/link")
@Before(AdminLoginInterceptor.class)
public class LinkController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/link/";
    @Resource
    private ILinkService linkService;

    @RequestMapping("/list")
    public String list(Model model){
        Page page = new Page(request);
        ResponseModel responseModel = linkService.listByPage(page);
        model.addAttribute("model",responseModel);
        return MANAGE_FTL_PATH + "list";
    }

    @RequestMapping("/add")
    public String add(){
        return MANAGE_FTL_PATH + "add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(Link link){
        ResponseModel responseModel = linkService.save(link);
        if(responseModel.getCode() == 0){
            responseModel.setCode(3);
        }
        return responseModel;
    }


    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Link link = linkService.findById(id);
        model.addAttribute("link",link);
        return MANAGE_FTL_PATH + "edit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Link link){
        if (link == null){
            return new ResponseModel(-1,"参数错误");
        }
        ResponseModel responseModel = linkService.update(link);
        if(responseModel.getCode() == 0){
            responseModel.setCode(3);
        }
        return responseModel;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id){
        return linkService.delete(id);
    }

    @RequestMapping("/enable/{id}")
    @ResponseBody
    public Object enable(@PathVariable("id") Integer id){
        return linkService.enable(id);
    }


}
