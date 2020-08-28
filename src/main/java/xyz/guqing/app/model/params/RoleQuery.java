package xyz.guqing.app.model.params;

import lombok.Data;
import xyz.guqing.app.model.support.QueryRequest;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Data
public class RoleQuery {
    private Long id;
    private String roleName;
    private String remark;
    private String createTime;
    private QueryRequest queryRequest = new QueryRequest();
}
