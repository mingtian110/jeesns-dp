package com.luntan.deppon.service.weibo.impl;

import com.luntan.deppon.dao.weibo.IWeiboFavorDao;
import com.luntan.deppon.model.weibo.WeiboFavor;
import com.luntan.deppon.service.weibo.IWeiboFavorService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2017/2/8.
 */
@Service("weiboFavorService")
public class WeiboFavorServiceImpl implements IWeiboFavorService {
    @Resource
    private IWeiboFavorDao weiboFavorDao;


    @Override
    public WeiboFavor find(Integer weiboId, Integer memberId) {
        return weiboFavorDao.find(weiboId,memberId);
    }

    @Override
    public void save(Integer weiboId, Integer memberId) {
        weiboFavorDao.save(weiboId,memberId);
    }

    @Override
    public void delete(Integer weiboId, Integer memberId) {
        weiboFavorDao.delete(weiboId,memberId);
    }
}
