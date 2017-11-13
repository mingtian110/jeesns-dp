package com.luntan.deppon.service.common;


import com.luntan.deppon.model.common.ArchiveFavor;

/**
 * 文章点赞Service接口
 * Created by cubc-luntan 2017/2/9.
 */
public interface IArchiveFavorService {

    ArchiveFavor find(Integer archiveId, Integer memberId);

    void save(Integer archiveId, Integer memberId);

    void delete(Integer archiveId, Integer memberId);
}