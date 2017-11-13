package com.luntan.deppon.dao.member;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.member.ValidateCode;
import org.apache.ibatis.annotations.Param;

/**
 * 验证码DAO接口
 * Created by cubc-luntan 17/01/20.
 */
public interface IValidateCodeDao extends IBaseDao<ValidateCode> {

    /**
     * 验证，30分钟以内有效
     * @param email
     * @param code
     * @param type
     * @return
     */
    ValidateCode valid(@Param("email") String email, @Param("code") String code, @Param("type") int type);

    int used(@Param("id") Integer id);
}