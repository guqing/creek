package xyz.guqing.app.model.params;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-11
 */
@Data
public class ActionLogQuery {
    private String username;
    /**
     * 操作内容
     */
    private String operation;

    private String location;

    /**
     * 日志创建开始时间
     */
    private LocalDateTime createFrom;

    /**
     * 日志创建结束时间
     */
    private LocalDateTime createTo;
}
