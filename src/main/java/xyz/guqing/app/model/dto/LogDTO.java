package xyz.guqing.app.model.dto;

import lombok.Data;
import xyz.guqing.app.model.entity.Log;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.support.OutputConverter;

import java.util.Date;

import static xyz.guqing.app.utils.BeanUtils.updateProperties;

/**
 * @author guqing
 * @date 2020-01-11 18:07
 */
@Data
public class LogDTO implements OutputConverter<LogDTO, Log> {
    private Long id;

    private String name;

    private String content;

    private Integer type;

    private String methodName;

    private String ip;

    private Date createTime;

    private Date modifyTime;

    private UserDTO user;

    @Override
    public <T extends LogDTO> T convertFrom(Log log) {
        updateProperties(log, this);
        this.user = new UserDTO().convertFrom(log.getUser());
        return (T)this;
    }
}
