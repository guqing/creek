package xyz.guqing.app.model.dto;

import lombok.Data;
import xyz.guqing.app.model.entity.UserLoginLog;
import xyz.guqing.app.model.support.OutputConverter;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Data
public class UserLoginLogDTO implements OutputConverter<UserLoginLogDTO, UserLoginLog> {
    private Long id;

    private String username;

    private LocalDateTime loginTime;

    private String location;

    private String ip;

    private String system;

    private String browser;
}
