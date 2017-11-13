package com.luntan.deppon.service.common;

import com.luntan.deppon.model.common.Archive;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.model.member.Member;


/**
 * Created by cubc-luntan 2016/10/14.
 */
public interface IArchiveService {

    Archive findByArchiveId(int id);

    boolean save(Member member, Archive archive);

    boolean update(Member member, Archive archive);

    boolean delete(int id);

    void updateViewCount(int id);

    ResponseModel favor(Member loginMember, int archiveId);
}
