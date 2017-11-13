package com.luntan.deppon.service.system;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.model.system.ScoreRule;

import java.util.List;

/**
 * Created by cubc-luntan 2017/2/14.
 */
public interface IScoreRuleService {

    List<ScoreRule> list();

    ScoreRule findById(Integer id);

    ResponseModel update(ScoreRule scoreRule);

    ResponseModel enabled(int id);

}
