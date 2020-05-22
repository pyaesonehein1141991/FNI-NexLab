package org.tat.fni.api.common;

import org.tat.fni.api.domain.User;

public interface IUserProcessService {
	public void registerUser(User user);
	public User getLoginUser();
}
