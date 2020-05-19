package org.tat.fni.api.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tat.fni.api.domain.Attachment;

public class AttachmentDTO {
	private String tempId;
	private String name;
	private String path;
	private String holderId;

	public AttachmentDTO() {
		tempId = System.currentTimeMillis() + "";
	}

	public AttachmentDTO(String name, String path) {
		tempId = System.currentTimeMillis() + "";
		this.name = name;
		this.path = path;
	}

	public AttachmentDTO(Attachment att) {
		this.name = att.getName();
		this.path = att.getFilePath();
	}

	public String getHolderId() {
		return holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
