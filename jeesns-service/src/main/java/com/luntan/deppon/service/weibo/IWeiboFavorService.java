package com.luntan.deppon.service.weibo;


import com.luntan.deppon.model.weibo.WeiboFavor;

/**
 * 微博点赞Service接口
 * Created by cubc-luntan 2017/2/8.
 */
public interface IWeiboFavorService {

    WeiboFavor find(Integer weiboId, Integer memberId);

    void save(Integer weiboId, Integer memberId);

    void delete(Integer weiboId, Integer memberId);
}