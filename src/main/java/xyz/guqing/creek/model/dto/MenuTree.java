package xyz.guqing.creek.model.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.creek.model.entity.Menu;
import xyz.guqing.creek.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {
    private String icon;
}
