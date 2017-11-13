package com.luntan.deppon.service.member.impl;

import com.luntan.deppon.dao.member.IValidateCodeDao;
import com.luntan.deppon.model.member.ValidateCode;
import com.luntan.deppon.service.member.IValidateCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/1/20.
 */
@Service("validateCodeService")
public class ValidateCodeServiceImpl implements IValidateCodeService {
    @Resource
    private IValidateCodeDao validateCodeDao;

    @Override
    public boolean save(ValidateCode validateCode) {
        return validateCodeDao.save(validateCode) == 1;
    }

    @Override
    public ValidateCode valid(String email, String code, int type) {
        return validateCodeDao.valid(email,code,type);
    }

    @Override
    public boolean used(Integer id) {
        return validateCodeDao.used(id) == 1;
    }
}
