package xyz.guqing.app.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginLogParam {
    private String username;
    private LocalDateTime createFrom;
    private LocalDateTime createTo;
}
