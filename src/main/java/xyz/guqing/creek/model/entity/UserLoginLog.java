package xyz.guqing.creek.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author guqing
 * @since 2020-08-19
 */
@Data
@Accessors(chain = true)
@TableName("user_login_log")
public class UserLoginLog {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    private String location;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作系统
     */
    private String system;

    /**
     * 浏览器
     */
    private String browser;
}
