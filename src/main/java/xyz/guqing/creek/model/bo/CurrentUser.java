package xyz.guqing.creek.model.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.entity.User;

/**
 * @author guqing
 * @date 2020-05-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CurrentUser extends User implements Serializable {

    private List<Role> roles;

    @JsonIgnore
    @JSONField(serialize = false)
    public String getPassword() {
        return super.getPassword();
    }
}
