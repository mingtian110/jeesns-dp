package com.luntan.deppon.dao.common;

import com.luntan.deppon.model.common.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cubc-luntan 2017/3/1.
 */
public interface IPictureDao extends IBaseDao<Picture> {

    List<Picture> find(@Param("id") Integer foreignId);

    int delete(@Param("id") Integer foreignId);

    int update(@Param("foreignId") Integer foreignId, @Param("ids") String[] ids);
    /*type为2的为banner图片*/
    List<Picture> findBanner();
    int updateBanner(@Param("path") String path, @Param("pictureId") Integer pictureId);
}