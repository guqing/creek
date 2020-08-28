package xyz.guqing.app.model.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.app.model.entity.Menu;
import xyz.guqing.app.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {
    private String icon;
    private String type;
    private String perms;
}
