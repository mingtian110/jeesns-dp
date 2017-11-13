package com.luntan.deppon.service.system.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.system.IActionLogDao;
import com.luntan.deppon.model.system.Action;
import com.luntan.deppon.model.system.ActionLog;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.service.system.IActionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2017/2/14.
 */
@Service("actionLogService")
public class ActionLogServiceImpl implements IActionLogService {
    @Resource
    private IActionService actionService;
    @Resource
    private IActionLogDao actionLogDao;

    @Override
    public ResponseModel<ActionLog> listByPage(Page page, Integer memberId) {
        List<ActionLog> list = actionLogDao.listByPage(page, memberId);
        ResponseModel model = new ResponseModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public ResponseModel<ActionLog> memberActionLog(Page page, Integer memberId) {
        List<ActionLog> list = actionLogDao.memberActionLog(page, memberId);
        ResponseModel model = new ResponseModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public ActionLog findById(Integer id) {
        return actionLogDao.findById(id);
    }

    @Override
    public void save(String actionIp, Integer memberId, Integer actionId) {
        this.save(actionIp,memberId,actionId,"",0,0);
    }

    @Override
    public void save(String actionIp, Integer memberId, Integer actionId, String remark) {
        this.save(actionIp,memberId,actionId,remark,0,0);
    }

    @Override
    public void save(String actionIp, Integer memberId, Integer actionId, String remark, Integer type, Integer foreignId) {
        Action action = actionService.findById(actionId);
        if(action != null){
            if(action.getStatus() == 0){
                ActionLog actionLog = new ActionLog(memberId,actionId,remark,actionIp,type,foreignId);
                actionLogDao.save(actionLog);
            }
        }
    }

}
