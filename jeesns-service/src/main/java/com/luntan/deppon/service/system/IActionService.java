package com.luntan.deppon.service.system;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.model.system.Action;

import java.util.List;

/**
 * Created by cubc-luntan 2017/2/14.
 */
public interface IActionService {

    List<Action> list();

    Action findById(Integer id);

    ResponseModel update(Action action);

    ResponseModel isenable(Integer id);

    boolean canuse(Integer id);
}
