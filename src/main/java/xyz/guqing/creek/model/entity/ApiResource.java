package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author guqing
 * @since 2022-01-12
 */
@Data
@TableName("api_resource")
@EqualsAndHashCode(callSuper = true)
public class ApiResource extends BaseEntity {

    private String name;
    private String displayName;
    private String description;
    private Long sortIndex;
}
