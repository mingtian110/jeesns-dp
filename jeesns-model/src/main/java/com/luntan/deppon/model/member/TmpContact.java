package com.luntan.deppon.model.member;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员实体类
 * Created by cubc-luntan 16/9/26.
 */
public class TmpContact implements Serializable {
	private Integer id;
	private Integer ownId;
	private Integer contactId;
	private String contactType;

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnId() {
		return ownId;
	}

	public void setOwnId(Integer ownId) {
		this.ownId = ownId;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
}