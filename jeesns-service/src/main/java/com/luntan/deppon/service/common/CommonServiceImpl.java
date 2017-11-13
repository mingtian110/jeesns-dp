package com.luntan.deppon.service.common;

import com.luntan.deppon.dao.common.ICommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/2/6.
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {
    @Resource
    private ICommonDao commonDao;

    @Override
    public String getMysqlVsesion() {
        return commonDao.getMysqlVsesion();
    }
}
