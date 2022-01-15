package xyz.guqing.creek.model.dto;

import java.time.LocalDateTime;
import xyz.guqing.creek.model.entity.Credentials;
import xyz.guqing.creek.model.support.OutputConverter;

/**
 * @author guqing
 * @since 2022-01-15
 */
public class CredentialsDTO implements OutputConverter<CredentialsDTO, Credentials> {

    private String remark;
    private String scopes;
    private LocalDateTime expiredAt;
}
