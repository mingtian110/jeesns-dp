package com.luntan.deppon.service.group.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.core.utils.StringUtils;
import com.luntan.deppon.model.group.GroupTopic;
import com.luntan.deppon.model.group.GroupTopicComment;
import com.luntan.deppon.model.member.Member;
import com.luntan.deppon.service.system.IActionLogService;
import com.luntan.deppon.service.group.IGroupTopicCommentService;
import com.luntan.deppon.dao.group.IGroupTopicCommentDao;
import com.luntan.deppon.service.group.IGroupTopicService;
import com.luntan.deppon.service.member.IScoreDetailService;
import com.luntan.deppon.common.utils.ActionUtil;
import com.luntan.deppon.common.utils.ScoreRuleConsts;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cubc-luntan 2016/12/27.
 */
@Service("groupTopicCommentService")
public class GroupTopicCommentServiceImpl implements IGroupTopicCommentService {
    @Resource
    private IGroupTopicCommentDao groupTopicCommentDao;
    @Resource
    private IGroupTopicService groupTopicService;
    @Resource
    private IActionLogService actionLogService;
    @Resource
    private IScoreDetailService scoreDetailService;

    @Override
    public GroupTopicComment findById(int id) {
        return groupTopicCommentDao.findById(id);
    }

    @Override
    public ResponseModel save(Member loginMember, String content, Integer groupTopicId,Integer commentId) {
        GroupTopic groupTopic = groupTopicService.findById(groupTopicId,loginMember);
        if(groupTopic == null){
            return new ResponseModel(-1,"帖子不存在");
        }
        if(StringUtils.isEmpty(content)){
            return new ResponseModel(-1,"内容不能为空");
        }
        GroupTopicComment groupTopicComment = new GroupTopicComment();
        groupTopicComment.setMemberId(loginMember.getId());
        groupTopicComment.setGroupTopicId(groupTopicId);
        groupTopicComment.setContent(content);
        groupTopicComment.setCommentId(commentId);
        int result = groupTopicCommentDao.save(groupTopicComment);
        if(result == 1){
            //群组帖子评论奖励
//            scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.GROUP_TOPIC_COMMENTS, groupTopicComment.getId());
            return new ResponseModel(1,"评论成功");
        }else {
            return new ResponseModel(-1,"评论失败");
        }
    }

    @Override
    public ResponseModel listByGroupTopic(Page page, int groupTopicId) {
        List<GroupTopicComment> list = groupTopicCommentDao.listByGroupTopic(page, groupTopicId);
        ResponseModel model = new ResponseModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByTopic(int groupTopicId) {
        groupTopicCommentDao.deleteByTopic(groupTopicId);
    }

    @Override
    public ResponseModel delete(Member loginMember,int id){
        GroupTopicComment groupTopicComment = this.findById(id);
        if(groupTopicComment == null){
            return new ResponseModel(-1,"评论不存在");
        }
        int result = groupTopicCommentDao.delete(id);
        if(result == 1){
            //扣除积分
            scoreDetailService.scoreCancelBonus(loginMember.getId(),ScoreRuleConsts.GROUP_TOPIC_COMMENTS,id);
            actionLogService.save(loginMember.getCurrLoginIp(),loginMember.getId(), ActionUtil.DELETE_GROUP_TOPIC_COMMENT,"ID："+groupTopicComment.getId()+"，内容："+groupTopicComment.getContent());
            return new ResponseModel(1,"删除成功");
        }
        return new ResponseModel(-1,"删除失败");
    }

}
