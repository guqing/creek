package xyz.guqing.app.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户分组表
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_group")
public class UserGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级分组id
     */
    private Long parentId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 排序
     */
    private Long sortIndex;
}
