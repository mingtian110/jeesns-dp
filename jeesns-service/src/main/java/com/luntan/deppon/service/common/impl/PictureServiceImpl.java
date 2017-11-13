package com.luntan.deppon.service.common.impl;

import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.dao.common.IPictureDao;
import com.luntan.deppon.model.common.Picture;
import com.luntan.deppon.service.common.IPictureService;
import com.luntan.deppon.common.utils.PictureUtil;
import com.luntan.deppon.core.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by cubc-luntan 2017/3/7.
 */
@Service
public class PictureServiceImpl implements IPictureService {
    @Resource
    private IPictureDao pictureDao;

    @Override
    public List<Picture> find(Integer foreignId) {
        return pictureDao.find(foreignId);
    }

    @Override
    public int delete(HttpServletRequest request, Integer foreignId) {
        List<Picture> pictures = this.find(foreignId);
        PictureUtil.delete(request,pictures);
        return pictureDao.delete(foreignId);
    }

    @Override
    public int save(Picture picture) {
        return pictureDao.save(picture);
    }

    @Override
    public int update(Integer foreignId, String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] idsArr = ids.split(",");
            return pictureDao.update(foreignId, idsArr);
        }
        return 0;
    }
    @Override
    public ResponseModel<Picture> findBanner(Page page) {
        ResponseModel model = new ResponseModel(0, page);
        List<Picture> banner = pictureDao.findBanner();
        model.setData(banner);
        return model;
    }
    @Override
    public int updateBanner(Picture picture) {
        return pictureDao.updateBanner(picture.getPath(),picture.getPictureId());
    }
}
