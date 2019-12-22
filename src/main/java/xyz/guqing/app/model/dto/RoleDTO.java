package xyz.guqing.app.model.dto;

import lombok.Data;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.support.OutputConverter;

import java.util.Date;
import java.util.List;

/**
 * 角色DTO
 *
 * @author guqing
 * @date 2019-08-11 11:21
 */
@Data
public class RoleDTO implements OutputConverter<RoleDTO, Role> {
	private Integer id;

	private String description;

	private String name;

	private String available;

	private Date createTime;

	private Date modifyTime;

	private List<PermissionDTO> permissions;

	private List<Long> permissionIds;
}
