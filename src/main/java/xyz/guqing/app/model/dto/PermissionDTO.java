package xyz.guqing.app.model.dto;

import lombok.Data;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.entity.Resource;
import xyz.guqing.app.model.support.OutputConverter;

import java.util.Set;

/**
 * 权限DTO
 *roleId': 'admin',
 * 'permissionId': 'dashboard',
 * 'permissionName': '仪表盘',
 * 'actions': '[{"action":"add","defaultCheck":false,"describe":"新增"}]',
 * 'actionEntitySet': [{
 * 	'action': 'add',
 * 	'describe': '新增',
 * 	'defaultCheck': false
 * }, {
 * 	'action': 'query',
 * 	'describe': '查询',
 * 	'defaultCheck': false
 * }
 * @author guqin
 * @date 2019-08-11 11:29
 */
@Data
public class PermissionDTO implements OutputConverter<PermissionDTO, Permission> {
	private Integer id;
	private String identify;
	private String name;
	private String description;
	private Integer available;
	private String actions;
	private Set<Resource> actionEntitySet;
}
