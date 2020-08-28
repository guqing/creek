package xyz.guqing.app.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户操作日志表
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@Accessors(chain = true)
@TableName("action_log")
public class ActionLog {

    private static final long serialVersionUID = 1L;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 执行时间，单位毫秒
     */
    private Long executionTime;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作者IP
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
