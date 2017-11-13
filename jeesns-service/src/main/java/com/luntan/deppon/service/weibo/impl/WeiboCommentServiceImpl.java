package com.luntan.deppon.service.weibo.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.StringUtils;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.service.weibo.IWeiboCommentService;
import com.luntan.deppon.core.utils.*;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.model.weibo.Weibo;
import com.luntan.deppon.model.weibo.WeiboComment;
import com.luntan.deppon.service.member.IScoreDetailService;
import com.luntan.deppon.dao.weibo.IWeiboCommentDao;
import com.luntan.deppon.service.weibo.IWeiboService;
import com.luntan.deppon.common.utils.ActionUtil;
import com.luntan.deppon.common.utils.ScoreRuleConsts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2016/12/22.
 */
@Service("weiboCommentService")
public class WeiboCommentServiceImpl implements IWeiboCommentService {
    @Resource
    private IWeiboCommentDao weiboCommentDao;
    @Resource
    private IWeiboService weiboService;
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private IScoreDetailService scoreDetailService;
    public static String weiboAlias="";

    @Override
    public WeiboComment findById(int id) {
        WeiboComment weiboComment = weiboCommentDao.findById(id);
        return weiboComment;
    }

    @Override
    public ResponseModel save(Member loginMember, String content, Integer weiboId, Integer weiboCommentId) {
        Weibo weibo = weiboService.findById(weiboId,loginMember.getId());
        if(weibo == null){
            return new ResponseModel(-1,weiboAlias+"不存在");
        }
        if(StringUtils.isEmpty(content)){
            return new ResponseModel(-1,"内容不能为空");
        }
        if(content.length() > 500){
            return new ResponseModel(-1,"评论内容不能超过500长度");
        }
        WeiboComment weiboComment = new WeiboComment();
        weiboComment.setMemberId(loginMember.getId());
        weiboComment.setWeiboId(weiboId);
        weiboComment.setContent(content);
        weiboComment.setCommentId(weiboCommentId);
        int result = weiboCommentDao.save(weiboComment);
        if(result == 1){
            //微博评论奖励
//            scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.COMMENT_WEIBO, weiboComment.getId());
            return new ResponseModel(1,"评论成功");
        }else {
            return new ResponseModel(-1,"评论失败");
        }
    }

    @Override
    public ResponseModel listByWeibo(Page page, int weiboId) {
        List<WeiboComment> list = weiboCommentDao.listByWeibo(page, weiboId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByWeibo(Integer weiboId) {
        weiboCommentDao.deleteByWeibo(weiboId);
    }

    @Override
    public ResponseModel delete(Member loginMember, int id) {
        WeiboComment weiboComment = this.findById(id);
        if(weiboComment == null){
            return new ResponseModel(-1,"评论不存在");
        }
        int result = weiboCommentDao.delete(id);
        if(result == 1){
            actionLogService.save(loginMember.getCurrLoginIp(),loginMember.getId(), ActionUtil.DELETE_WEIBO_COMMENT,"ID："+weiboComment.getId()+"内容："+weiboComment.getContent());
            return new ResponseModel(1,"删除成功");
        }
        return new ResponseModel(-1,"删除失败");
    }

}
