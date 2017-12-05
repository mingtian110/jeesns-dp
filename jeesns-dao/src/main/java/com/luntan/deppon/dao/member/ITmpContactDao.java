package com.luntan.deppon.dao.member;

import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.TmpContact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员DAO接口
 * Created by cubc-luntan 16/9/26.
 */
public interface ITmpContactDao extends IBaseDao<TmpContact> {

    List<TmpContact> listByPage(@Param("page") Page page, @Param("ownId") Integer ownId);
    Integer insert(TmpContact tmpContact);
    Integer delete(TmpContact tmpContact);
    List<TmpContact> select(TmpContact tmpContact);

}