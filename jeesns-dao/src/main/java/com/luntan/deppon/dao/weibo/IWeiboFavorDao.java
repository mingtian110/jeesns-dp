package com.luntan.deppon.dao.weibo;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.weibo.WeiboFavor;
import org.apache.ibatis.annotations.Param;

/**
 * 微博点赞DAO接口
 * Created by cubc-luntan 2017/2/8.
 */
public interface IWeiboFavorDao extends IBaseDao<WeiboFavor> {

    WeiboFavor find(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);

    Integer save(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);

    Integer delete(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);
}