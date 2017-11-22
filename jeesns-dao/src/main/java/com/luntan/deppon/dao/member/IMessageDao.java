package com.luntan.deppon.dao.member;

import com.luntan.deppon.dao.common.IBaseDao;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.member.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员私信DAO
 * Created by cubc-luntan 17/3/9.
 */
public interface IMessageDao extends IBaseDao<Message> {
    List<Message> listByPage(@Param("page") Page page, @Param("fromMemberId") Integer fromMemberId, @Param("toMemberId") Integer toMemberId);

    /**
     * 获取聊提记录
     * @param page
     * @param fromMemberId
     * @param toMemberId
     * @return
     */
    List<Message> messageRecords(@Param("page") Page page, @Param("fromMemberId") Integer fromMemberId, @Param("toMemberId") Integer toMemberId);
    Integer messageRecordsDelete( @Param("messageId") Integer messageId);

    /**
     * 删除某个会员的所有聊天记录
     * @param memberId
     * @return
     */
    int deleteByMember(@Param("memberId") Integer memberId);

    /**
     * 清空两个会员的聊天记录
     * @param fromMemberId
     * @param toMemberId
     * @return
     */
    int clearMessageByMember(@Param("fromMemberId") Integer fromMemberId, @Param("toMemberId") Integer toMemberId);

    /**
     * 查询会员未读私信数量
     * @param memberId
     * @return
     */
    int countUnreadNum(@Param("memberId") Integer memberId);

    /**
     * 设置已读状态
     * @param fromMemberId
     * @param toMemberId
     * @return
     */
    int setRead(@Param("fromMemberId") Integer fromMemberId, @Param("toMemberId") Integer toMemberId);
}