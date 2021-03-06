package com.luntan.deppon.service.member.impl;

import com.luntan.deppon.common.utils.IdGenerator;
import com.luntan.deppon.common.utils.SequenceManager;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.common.INoticeDao;
import com.luntan.deppon.dao.member.IMemberFansDao;
import com.luntan.deppon.model.common.Notice;
import com.luntan.deppon.model.member.MemberFans;
import com.luntan.deppon.service.member.IMemberFansService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2017/2/21.
 */
@Service("memberFansServiceImpl")
public class MemberFansServiceImpl implements IMemberFansService {
    @Resource
    private IMemberFansDao memberFansDao;
    @Resource
    private INoticeDao noticeDao;
    @Override
    public MemberFans find(Integer whoFollowId, Integer followWhoId) {
        return memberFansDao.find(whoFollowId,followWhoId);
    }

    /**
     * 关注
     */
    @Override
    public ResponseModel save(Integer whoFollowId, Integer followWhoId) {
        if(memberFansDao.find(whoFollowId,followWhoId) == null){
            if(memberFansDao.save(whoFollowId,followWhoId) == 1){
                return new ResponseModel(1,"关注成功");
            }
        }else {
            //已经关注了
            return new ResponseModel(0,"关注成功");
        }
        return new ResponseModel(-1,"关注失败");
    }

    /**
     * 取消关注
     */
    @Override
    public ResponseModel delete(Integer whoFollowId, Integer followWhoId) {
        if(memberFansDao.delete(whoFollowId,followWhoId) > 0){
            return new ResponseModel(1,"取消关注成功");
        }
        return new ResponseModel(-1,"取消关注失败");
    }

    @Override
    public ResponseModel followsList(Page page, Integer whoFollowId) {
        List<MemberFans> list = memberFansDao.followsList(page, whoFollowId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public ResponseModel fansList(Page page, Integer followWhoId) {
        List<MemberFans> list = memberFansDao.fansList(page, followWhoId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }


    @Override
    public ResponseModel noticeList(Page page, Integer followWhoId) {
        String toId = SequenceManager.returnToId(followWhoId);
        List<Notice> list = noticeDao.listByPage(page, toId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }


}
