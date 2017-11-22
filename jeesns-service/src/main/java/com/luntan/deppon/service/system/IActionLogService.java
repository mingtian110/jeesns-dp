package com.luntan.deppon.service.system;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.system.ActionLog;

/**
 * Created by cubc-luntan 2017/2/14.
 */
public interface IActionLogService {

    ResponseModel<ActionLog> listByPage(Page page, Integer memberId);

    ResponseModel<ActionLog> memberActionLog(Page page, Integer memberId);
    ResponseModel<ActionLog> memberActionLogFocus(Page page, Integer[] memberIds);

    ActionLog findById(Integer id);

    void save(String actionIp,Integer memberId, Integer actionId);

    void save(String actionIp,Integer memberId, Integer actionId,String remark);

    void save(String actionIp,Integer memberId, Integer actionId,String remark, Integer type, Integer foreignId);

}
