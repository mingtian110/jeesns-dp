package com.luntan.deppon.service.system.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.dao.system.IScoreRuleDao;
import com.luntan.deppon.model.system.ScoreRule;
import com.luntan.deppon.service.system.IScoreRuleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2017/3/24.
 */
@Service
public class ScoreRuleServiceImpl implements IScoreRuleService {
    @Resource
    private IScoreRuleDao scoreRuleDao;


    @Override
    public List<ScoreRule> list() {
        return scoreRuleDao.allList();
    }

    @Override
    public ScoreRule findById(Integer id) {
        return scoreRuleDao.findById(id);
    }

    @Override
    public ResponseModel update(ScoreRule scoreRule) {
        if(scoreRuleDao.update(scoreRule) == 1){
            return new ResponseModel(0, "操作成功");
        }
        return new ResponseModel(-1, "操作失败");
    }

    @Override
    public ResponseModel enabled(int id) {
        if(scoreRuleDao.enabled(id) == 1){
            return new ResponseModel(0, "操作成功");
        }
        return new ResponseModel(-1, "操作失败");
    }

}
