package com.luntan.deppon.service.common;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.common.Picture;
import com.luntan.deppon.model.system.ActionLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cubc-luntan 2017/3/7.
 */
public interface IPictureService {

    List<Picture> find(Integer foreignId);

    int delete(HttpServletRequest request, Integer foreignId);

    int save(Picture picture);

    ResponseModel<Picture> findBanner(Page page);
    int updateBanner(Picture picture);
    int update(Integer foreignId, String ids);
}