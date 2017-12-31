package com.luntan.deppon.service.member.impl;

import com.deppon.cubc.bill.client.api.MessageService;
import com.deppon.cubc.commons.dao.Result;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.member.IMemberDao;
import com.luntan.deppon.dao.member.IMessageDao;
import com.luntan.deppon.dao.member.ITmpContactDao;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.member.Message;
import com.luntan.deppon.model.member.TmpContact;
import com.luntan.deppon.service.member.IMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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


    @Autowired(required = false)
    ITmpContactDao iTmpContactDao;
    @Override
    public ResponseModel save(Integer fromMemberId, Integer toMemberId, String content,HttpServletRequest request) {
        if(fromMemberId.intValue() == toMemberId.intValue()){
            return new ResponseModel(-1, "不能发信息给自己");
        }
//        Message message = new Message(fromMemberId, toMemberId, content);
        Message message = new Message();
        message.setFromMemberId(fromMemberId);
        message.setToMemberId(toMemberId);
        message.setContent(content);
        request.getServletContext().setAttribute(toMemberId+"_note",true);
        request.getServletContext().setAttribute(toMemberId+"_remind",true);
        int save=0;
        try{
             save = messageDao.save(message);
            TmpContact fromtmpContact=new TmpContact();
            fromtmpContact.setOwnId(fromMemberId);
            fromtmpContact.setContactId(toMemberId);
            fromtmpContact.setContactType("0");
            List<TmpContact> fromselect = iTmpContactDao.select(fromtmpContact);
            if(fromselect==null||fromselect.size()==0){
                iTmpContactDao.insert(fromtmpContact);
            }
            TmpContact totmpContact=new TmpContact();
            totmpContact.setOwnId(toMemberId);
            totmpContact.setContactId(fromMemberId);
            totmpContact.setContactType("0");
            List<TmpContact> toselect = iTmpContactDao.select(totmpContact);
            if(toselect==null||toselect.size()==0){
                iTmpContactDao.insert(totmpContact);
            }
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
    @Autowired(required = false)
    private IMemberDao memberDao;
    @Override
    public ResponseModel<Message> messageRecords(Page page, Integer fromMemberId, Integer toMemberId, HttpServletRequest request,String flag) {
        Boolean attribute = (Boolean)request.getServletContext().getAttribute(toMemberId + "_note");
        if("1".equals(flag)&&(attribute==null||!attribute)){
            ResponseModel model = new ResponseModel(0, page);
            model.setData(new ArrayList());
            model.setMessage("无新消息");
            return model;
        }
        //设置该会员聊天记录为已读
        int i = this.setRead(fromMemberId, toMemberId);
        ResponseModel model = new ResponseModel(0, page);
        //如果有更新,则发信人有消息变动提示 发信人读取导通知后就更新状态为已无消息
        if(i>0){
            request.getServletContext().setAttribute(fromMemberId+"_note",true);
        }
        request.getServletContext().setAttribute( toMemberId+"_note",false);
        List<Message> list = messageDao.messageRecordsByTmpContact(page, fromMemberId, toMemberId);
        Member   fromMember = memberDao.findById(fromMemberId);
        Member   toMember = memberDao.findById(toMemberId);
        for(Message item:list){
            Integer fromMemberId1 = fromMember.getId();
            if(fromMemberId1.equals(item.getFromMemberId())){
                item.setFromMember(fromMember);
                item.setToMember(toMember);
            }else{
                item.setFromMember(toMember);
                item.setToMember(fromMember);
            }
        }
        model.setCode(1);
        model.setData(list);
        model.setMessage("有新消息");
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
        int i = messageDao.setRead(fromMemberId, toMemberId);
        logger.error("tomemberId:"+toMemberId);
        logger.error("已读消息:"+i);
        return i;
    }

    /**
     * 发送信息
     * @param loginMember
     * @param findMember
     * @return
     */
    @Override
    public ResponseModel sendMsg(Member loginMember, Member findMember){
        String content="你好,我是"+loginMember.getName()+",我在cubc-luntan向你发了一条消息,烦请登录cubc,点击右上角登录论坛查看,我的手机号是:"+loginMember.getPhone()+",你也可以直接电话联系我\n";
        if(StringUtils.isBlank(findMember.getPhone())){
            return new ResponseModel(0, "对方手机号为空");
        }
        if(StringUtils.isBlank(loginMember.getPhone())){
            return new ResponseModel(0, "你的手机号为空");
        }
        Result<String> stringResult = billMessageService.sendMessage(findMember.getPhone(), content);
        if(stringResult.isSuccess()) {
            return new ResponseModel(0, "短信已发送至:"+findMember.getPhone());
        }else {
            return new ResponseModel(-1, "测试环境信息发送失败");
        }
    }
}
