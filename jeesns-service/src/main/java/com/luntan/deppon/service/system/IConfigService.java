package com.luntan.deppon.service.system;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.model.system.Config;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by cubc-luntan 16/9/29.
 */
public interface IConfigService {
    List<Config> allList();

    Map<String,String> getConfigToMap();

    String getValue(String key);

    ResponseModel update(Map<String,String> params, HttpServletRequest request);
}
