package com.luntan.deppon.service.system.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.dao.system.IConfigDao;
import com.luntan.deppon.model.system.Config;
import com.luntan.deppon.service.system.IConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cubc-luntan 16/9/27.
 */
@Service("configService")
public class ConfigServiceImpl implements IConfigService {
    @Resource
    private IConfigDao configDao;


    @Override
    public List<Config> allList() {
        return configDao.allList();
    }

    @Override
    public Map<String, String> getConfigToMap() {
        List<Config> allList = this.allList();
        Map<String,String> map = new HashMap<>();
        for (Config config : allList) {
            map.put(config.getJkey(),config.getJvalue());
        }
        return map;
    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public ResponseModel update(Map<String, String> params, HttpServletRequest request) {
        for(Map.Entry entry : params.entrySet()){
            if(((String)entry.getValue()).length() > 500){
                return new ResponseModel(-1,"只能输入255个字符");
            }else {
                configDao.update((String)entry.getKey(),(String)entry.getValue());
                request.getServletContext().setAttribute(((String)entry.getKey()).toUpperCase(),entry.getValue());
            }
        }
        return new ResponseModel(0,"操作成功");
    }


}
