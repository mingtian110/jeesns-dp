package com.luntan.deppon.service.cms;


import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.model.cms.ArticleCate;

import java.util.List;


/**
 * Created by cubc-luntan 16/9/29.
 */
public interface IArticleCateService {

    ArticleCate findById(int id);

    int save(ArticleCate articleCate);

    int update(ArticleCate articleCate);

    ResponseModel delete(int id);

    /**
     * 获取栏目
     * @return
     */
    List<ArticleCate> list();

    /**
     * 通过父类ID获取子类列表
     * @param fid
     * @return
     */
    List<ArticleCate> findListByFid(int fid);
}
