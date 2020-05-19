package org.tat.fni.api.common;



public class CommonDTO {
	private String id;
	private boolean existsEntity;
	private int version;
	private UserRecorder recorder;

	public CommonDTO() {
		super();
	}

	public CommonDTO(boolean existsEntity, int version, String id, UserRecorder recorder) {
		this.id = id;
		this.existsEntity = existsEntity;
		this.version = version;
		this.recorder = recorder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (existsEntity ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonDTO other = (CommonDTO) obj;
		if (existsEntity != other.existsEntity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
