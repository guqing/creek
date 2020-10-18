package xyz.guqing.creek.model.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import xyz.guqing.creek.model.entity.Menu;
import xyz.guqing.creek.model.enums.MenuType;
import xyz.guqing.creek.model.support.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author guqing
 * @date 2020-06-06
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuParam implements InputConverter<Menu> {
    private Long id;

    private Long parentId;

    @NotBlank(message = "菜单标题不能为空")
    @Size(max = 150, message = "菜单标题不能字符长度不能大于 {max}")
    private String title;

    private String type;

    private String path;

    private String redirect;

    private String name;

    private String component;

    private String perms;

    private String icon;

    private Boolean hidden;

    private Boolean keepAlive;

    private Long sortIndex;

    @Override
    public Menu convertTo() {
        if(parentId == null) {
            parentId = 0L;
        }
        if(sortIndex == null) {
            sortIndex = 0L;
        }

        if(keepAlive == null) {
            keepAlive = false;
        }

        type = MenuType.valueFrom(type);

        return InputConverter.super.convertTo();
    }
}
