package com.luntan.deppon.service.common;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.common.Link;

/**
 * Created by cubc-luntan 2017-10-13.
 */
public interface ILinkService {
   
    ResponseModel save(Link link);
   
    ResponseModel listByPage(Page page);

    ResponseModel allList();

    ResponseModel recommentList();

    ResponseModel update(Link link);

    ResponseModel delete(Integer id);

    Link findById(Integer id);

    ResponseModel enable(Integer id);
}
