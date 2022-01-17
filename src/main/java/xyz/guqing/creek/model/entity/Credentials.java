package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author guqing
 * @since 2022-01-14
 */
@Data
public class Credentials {

    @TableField(value = "id")
    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;

    private String token;

    private String remark;

    private LocalDateTime expiredAt;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

}
