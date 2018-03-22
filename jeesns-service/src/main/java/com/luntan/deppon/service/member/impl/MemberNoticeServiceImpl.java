package com.luntan.deppon.service.member.impl;

import com.luntan.deppon.common.utils.SequenceManager;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.common.INoticeDao;
import com.luntan.deppon.dao.member.IMemberFansDao;
import com.luntan.deppon.model.common.Notice;
import com.luntan.deppon.model.member.MemberFans;
import com.luntan.deppon.service.member.IMemberFansService;
import com.luntan.deppon.service.member.IMemberNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cubc-luntan 2017/2/21.
 */
@Service("memberNoticeService")
public class MemberNoticeServiceImpl implements IMemberNoticeService {
    @Resource
    private INoticeDao noticeDao;
    @Override
    public ResponseModel noticeList(Page page, Integer followWhoId) {
        String toId = SequenceManager.returnToId(followWhoId);
        List<Notice> list=new ArrayList<>();
        try {
            list = noticeDao.listByPage(page, toId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public Integer countNotice(Integer followWhoId) {
        String toId = SequenceManager.returnToId(followWhoId);
        int i = noticeDao.countUnread(toId);
        return i;
    }
}
