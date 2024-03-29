package xyz.guqing.creek.model.dto;

import java.util.Set;
import lombok.Data;
import xyz.guqing.creek.model.dos.UserDO;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.support.OutputConverter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserDTO implements OutputConverter<UserDTO, UserDO> {
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String mobile;

    private Long groupId;

    private String groupName;

    private Set<SimpleRoleDTO> roles;

    private String avatar;

    private String description;

    private Boolean isInternal;

    private Integer status;

    private LocalDateTime createTime;

}
