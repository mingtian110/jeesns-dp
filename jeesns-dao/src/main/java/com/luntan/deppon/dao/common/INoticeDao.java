package com.luntan.deppon.dao.common;

import com.luntan.deppon.core.model.Page;
import com.luntan.deppon.model.common.Ads;
import com.luntan.deppon.model.common.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by MMF on 2017-09-07.
 */
public interface INoticeDao {

    int save(Notice entity);
    int update(@Param("id") Long id);
    int countUnread(@Param("toId") String toId);
    /**
     * ∑÷“≥≤È—Ø
     * @param page
     * @return
     */
    List<Notice> listByPage(@Param("page") Page page, @Param("toId") String toId);

}
