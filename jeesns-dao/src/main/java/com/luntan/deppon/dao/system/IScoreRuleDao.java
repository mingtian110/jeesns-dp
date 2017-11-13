package com.luntan.deppon.dao.system;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.system.ScoreRule;
import org.apache.ibatis.annotations.Param;

/**
 * Created by cubc-luntan 2017/3/24.
 */
public interface IScoreRuleDao extends IBaseDao<ScoreRule> {

    int enabled(@Param("id") int id);
}
