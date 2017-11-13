package com.luntan.deppon.service.group;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.group.Group;
import com.luntan.deppon.model.member.Member;

import java.util.List;


/**
 * Created by cubc-luntan 16/12/23.
 */
public interface IGroupService {

    Group findById(int id);

    ResponseModel save(Member loginMember, Group group);

    ResponseModel update(Member loginMember, Group group);

    ResponseModel delete(Member loginMember, int id);

    ResponseModel listByPage(int status, Page page, String key);

    ResponseModel follow(Member loginMember, Integer groupId,int type);

    ResponseModel changeStatus(int id);

    List<Group> listByCustom(int status, int num, String sort);
}
