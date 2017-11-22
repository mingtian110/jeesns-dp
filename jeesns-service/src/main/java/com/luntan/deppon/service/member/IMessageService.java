package com.luntan.deppon.service.member;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.Message;

/**
 * 会员私信Service
 * Created by cubc-luntan 17/3/9.
 */
public interface IMessageService {

    ResponseModel save(Integer fromMemberId, Integer toMemberId, String content);
    ResponseModel sendMsg(Member loginMember,Member findMember);

    ResponseModel<Message> listByPage(Page page, Integer fromMemberId, Integer toMemberId);

    ResponseModel<Message> messageRecords(Page page, Integer fromMemberId, Integer toMemberId);
    ResponseModel<Message> messageRecordsDelete(Page page, Integer messageId, Integer toMemberId);

    /**
     * 删除某个会员的所有聊天记录
     * @param memberId
     * @return
     */
    int deleteByMember(Integer memberId);

    /**
     * 清空两个会员的聊天记录
     * @param fromMemberId
     * @param toMemberId
     * @return
     */
    int clearMessageByMember(Integer fromMemberId, Integer toMemberId);

    /**
     * 会员未读私信数量
     * @param memberId
     * @return
     */
    int countUnreadNum(int memberId);

    /**
     * 设置已读状态
     * @param fromMemberId
     * @param toMemberId
     * @return
     */
    int setRead(Integer fromMemberId, Integer toMemberId);
}