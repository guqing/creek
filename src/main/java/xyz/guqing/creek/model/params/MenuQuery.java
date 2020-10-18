package xyz.guqing.creek.model.params;

import lombok.Data;
import xyz.guqing.creek.model.entity.Menu;
import xyz.guqing.creek.model.support.InputConverter;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
public class MenuQuery implements InputConverter<Menu> {
    private Long orderIndex;
    private String type;
}
