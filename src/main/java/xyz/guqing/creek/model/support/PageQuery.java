package xyz.guqing.creek.model.support;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guqing
 */
@Data
@ToString
public class PageQuery implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    /**
     * 当前页面数据量
     */
    private Long pageSize = 10L;
    /**
     * 当前页码
     */
    private Long current = 1L;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;
}
