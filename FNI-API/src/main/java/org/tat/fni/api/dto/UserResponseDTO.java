package org.tat.fni.api.dto;

import java.util.List;

import org.tat.fni.api.common.emumdata.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserResponseDTO {

	@ApiModelProperty(position = 0)
	private Integer id;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)
	private String email;
	@ApiModelProperty(position = 3)
	List<Role> roles;

}
