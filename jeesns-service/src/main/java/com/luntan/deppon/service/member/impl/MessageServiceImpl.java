package com.luntan.deppon.service.member.impl;

import com.deppon.cubc.bill.client.api.MessageService;
import com.deppon.cubc.commons.dao.Result;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.member.IMessageDao;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.Message;
import com.luntan.deppon.service.member.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2017/3/9.
 */
@Service("messageService")
public class MessageServiceImpl implements IMessageService {
    @Resource
    private IMessageDao messageDao;
    @Resource
    private MessageService billMessageService;
    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


    @Override
    public ResponseModel save(Integer fromMemberId, Integer toMemberId, String content) {
        if(fromMemberId.intValue() == toMemberId.intValue()){
            return new ResponseModel(-1, "不能发信息给自己");
        }
//        Message message = new Message(fromMemberId, toMemberId, content);
        Message message = new Message();
        message.setFromMemberId(fromMemberId);
        message.setToMemberId(toMemberId);
        message.setContent(content);
        int save=0;
        try{
             save = messageDao.save(message);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
        if(save == 1){
            return new ResponseModel(0, "信息发送成功");
        }
        return new ResponseModel(-1, "信息发送失败");
    }

    @Override
    public ResponseModel<Message> listByPage(Page page, Integer fromMemberId, Integer toMemberId) {
        List<Message> list = messageDao.listByPage(page,fromMemberId, toMemberId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public ResponseModel<Message> messageRecords(Page page, Integer fromMemberId, Integer toMemberId) {
        //设置该会员聊天记录为已读
        int i = this.setRead(fromMemberId, toMemberId);
        List<Message> list = messageDao.messageRecords(page, fromMemberId, toMemberId);
        ResponseModel model = new ResponseModel(0, page);
        model.setData(list);
        if(i>0) {
            model.setCode(1);
        }else{
            model.setCode(0);
        }
//        model.setMessage("有新消息");
        return model;
    }
    @Override
    public ResponseModel<Message> messageRecordsDelete(Page page, Integer messageId, Integer toMemberId) {
        //设置该会员聊天记录为已读
        Integer count =0;
        try {
            //超过3分钟不允许撤回

            count = messageDao.messageRecordsDelete(messageId);
        }catch (Exception e){
            logger.error("",e);
        }
        ResponseModel model = new ResponseModel(0, page);
        if(count==1) {
            return new ResponseModel(0, "撤回成功");
        }else{
            return new ResponseModel(1, "已读无法撤回");
        }
//        if("1".equals(count+"")){
//            model.setCode(0);
//            model.setMessage("撤回成功");
//        }else{
//            model.setCode(1);
//            model.setMessage("撤回失败");
//        }
//        return model;
    }

    @Override
    public int deleteByMember(Integer memberId) {
        return messageDao.deleteByMember(memberId);
    }

    @Override
    public int clearMessageByMember(Integer fromMemberId, Integer toMemberId) {
        return messageDao.clearMessageByMember(fromMemberId, toMemberId);
    }

    @Override
    public int countUnreadNum(int memberId) {
        return messageDao.countUnreadNum(memberId);
    }

    @Override
    public int setRead(Integer fromMemberId, Integer toMemberId) {
        return messageDao.setRead(fromMemberId,toMemberId);
    }

    /**
     * 发送信息
     * @param loginMember
     * @param findMember
     * @return
     */
    @Override
    public ResponseModel sendMsg(Member loginMember, Member findMember){
        String content="我在cubc-luntan向你发了一条消息,烦请回复下\n --"+loginMember.getName();
        if(StringUtils.isBlank(findMember.getPhone())){
            return new ResponseModel(0, "对方手机号为空");
        }
        if(StringUtils.isBlank(loginMember.getPhone())){
            return new ResponseModel(0, "你的手机号为空");
        }
        Result<String> stringResult = billMessageService.sendMessage(findMember.getPhone(), content);
        if(stringResult.isSuccess()) {
            return new ResponseModel(0, "信息发送成功");
        }else {
            return new ResponseModel(-1, "测试环境信息发送失败");
        }
    }
}
