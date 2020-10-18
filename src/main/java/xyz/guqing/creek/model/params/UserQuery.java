package xyz.guqing.creek.model.params;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserQuery {
    private String username;
    private String nickname;
    private Long groupId;
    private String gender;
    private Integer status;
    private String mobile;
    private String groupName;
    private LocalDateTime createTimeFrom;
    private LocalDateTime createTimeTo;
}
