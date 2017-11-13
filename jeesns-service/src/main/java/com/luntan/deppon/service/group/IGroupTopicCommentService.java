package com.luntan.deppon.service.group;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.group.GroupTopicComment;
import com.luntan.deppon.model.member.Member;


/**
 * Created by cubc-luntan 2016/12/27.
 */
public interface IGroupTopicCommentService {

    GroupTopicComment findById(int id);

    ResponseModel save(Member loginMember, String content, Integer groupTopicId,Integer commentId);

    ResponseModel delete(Member loginMember,int id);

    ResponseModel listByGroupTopic(Page page, int groupTopicId);

    void deleteByTopic(int groupTopicId);
}
