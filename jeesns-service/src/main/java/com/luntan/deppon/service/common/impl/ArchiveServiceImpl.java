package com.luntan.deppon.service.common.impl;

import com.luntan.deppon.dao.common.IArchiveDao;
import com.luntan.deppon.model.common.Archive;
import com.luntan.deppon.service.common.IArchiveService;
import com.luntan.deppon.service.common.IArchiveFavorService;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.utils.HtmlUtil;
import com.luntan.deppon.core.utils.StringUtils;
import com.luntan.deppon.model.member.Member;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by cubc-luntan 2016/10/14.
 */
@Service("archiveService")
public class ArchiveServiceImpl implements IArchiveService {
    @Resource
    private IArchiveDao archiveDao;
    @Resource
    private IArchiveFavorService archiveFavorService;
//    @Resource
//    private IScoreRuleService scoreRuleService;

    @Override
    public Archive findByArchiveId(int id) {
        return archiveDao.findByArchiveId(id);
    }

    @Override
    public boolean save(Member member, Archive archive) {
        archive.setMemberId(member.getId());
        if (archive.getViewCount() == null) {
            archive.setViewCount(0);
        }
        if (archive.getViewRank() == null) {
            archive.setViewRank(0);
        }
        if (StringUtils.isEmpty(archive.getDescription())) {
            String contentStr = HtmlUtil.delHTMLTag(archive.getContent());
            if (contentStr.length() > 200) {
                archive.setDescription(contentStr.substring(0, 200));
            } else {
                archive.setDescription(contentStr);
            }
        }
        if (StringUtils.isEmpty(archive.getThumbnail())) {
            Document doc = Jsoup.parseBodyFragment(archive.getContent());
            Elements elements = doc.select("img[src]");
            if (elements.size() > 0) {
                String imgsrc = elements.get(0).attr("src");
                archive.setThumbnail(imgsrc);
            }
        }
        return archiveDao.save(archive) == 1;
    }

    @Override
    public void updateViewCount(int id) {
        archiveDao.updateViewCount(id);
    }

//    @Transactional
    @Override
    public ResponseModel favor(Member loginMember, int archiveId) {
        String message;
        ResponseModel<Integer> responseModel;
        if(archiveFavorService.find(archiveId,loginMember.getId()) == null){
            //增加
            archiveDao.favor(archiveId,1);
            archiveFavorService.save(archiveId,loginMember.getId());
            message = "喜欢成功";
            responseModel = new ResponseModel(0,message);
        }else {
            //减少
            archiveDao.favor(archiveId,-1);
            archiveFavorService.delete(archiveId,loginMember.getId());
            message = "取消喜欢成功";
            responseModel = new ResponseModel(1,message);
        }
        //这里可以懒加载,直接返回加1
        Archive findArchive = this.findByArchiveId(archiveId);
        responseModel.setData(findArchive.getFavor());
        return responseModel;
    }

    @Override
    public boolean update(Member member, Archive archive) {
        Archive findArchive = this.findByArchiveId(archive.getArchiveId());
        if (findArchive == null) {
            return false;
        }
        findArchive.setTitle(archive.getTitle());
        findArchive.setThumbnail(archive.getThumbnail());
        findArchive.setContent(archive.getContent());
        findArchive.setDescription(archive.getDescription());
        findArchive.setKeywords(archive.getKeywords());
        //普通会员
        if (member.getIsAdmin() == 0) {
            if (member.getId().intValue() != findArchive.getMemberId().intValue()) {
                return false;
            }
        } else {
            //管理员
            findArchive.setSource(archive.getSource());
            findArchive.setViewCount(archive.getViewCount());
            findArchive.setWriter(archive.getWriter());
            findArchive.setViewRank(archive.getViewRank());
        }
        if (findArchive.getViewCount() == null) {
            findArchive.setViewCount(0);
        }
        if (findArchive.getViewRank() == null) {
            findArchive.setViewRank(0);
        }
        if (StringUtils.isEmpty(findArchive.getDescription())) {
            String contentStr = HtmlUtil.delHTMLTag(findArchive.getContent());
            if (contentStr.length() > 200) {
                findArchive.setDescription(contentStr.substring(0, 200));
            } else {
                findArchive.setDescription(contentStr);
            }
        }
        if (StringUtils.isEmpty(findArchive.getThumbnail())) {
            Document doc = Jsoup.parseBodyFragment(findArchive.getContent());
            Elements elements = doc.select("img[src]");
            if (elements.size() > 0) {
                String imgsrc = elements.get(0).attr("src");
                findArchive.setThumbnail(imgsrc);
            }
        }
        return archiveDao.update(findArchive) == 1;
    }

    @Override
//    @Transactional
    public boolean delete(int id) {
        return archiveDao.delete(id) == 1;
    }

}
