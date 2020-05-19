package org.tat.fni.api.common;


public class ResourceQuestionDTO extends CommonDTO {

	private String id;
	private boolean exitingEntity;
	private String name;
	private int version;

	public ResourceQuestionDTO() {
		id = System.nanoTime() + "";
	}

	public ResourceQuestionDTO(String name) {
		id = System.nanoTime() + "";
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExitingEntity() {
		return exitingEntity;
	}

	public void setExitingEntity(boolean exitingEntity) {
		this.exitingEntity = exitingEntity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
