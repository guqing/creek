package xyz.guqing.creek.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guqing
 * @since 2022-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Credentials extends BaseEntity {
    private String token;
    private String remark;
}
