package xyz.guqing.creek.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author guqing
 * @date 2020-08-19
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginParam {
    private String username;
    private String password;
}
