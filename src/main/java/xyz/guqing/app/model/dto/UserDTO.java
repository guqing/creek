package xyz.guqing.app.model.dto;

import lombok.Data;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.support.OutputConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户信息dto
 *
 * @author guqing
 * @date 2019-08-11 9:29
 */
@Data
public class UserDTO implements OutputConverter<UserDTO,User> {
	private Integer id;
	private String name;
	private String username;
	private String description;
	private Date birthday;
	private String email;
	private String avatar;
	private Integer status;
	private Integer gender;
	private String telephone;
	private String lastLoginIp;
	private Date lastLoginTime;
	private Date createTime;
	private String roleId;
	private RoleDTO role;
}
