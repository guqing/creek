package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.model.annotation.ControllerEndpoint;
import xyz.guqing.app.model.bo.VueRouter;
import xyz.guqing.app.model.dto.MenuDTO;
import xyz.guqing.app.model.dto.MenuTree;
import xyz.guqing.app.model.entity.Menu;
import xyz.guqing.app.model.params.MenuParam;
import xyz.guqing.app.model.params.MenuQuery;
import xyz.guqing.app.model.support.ResultEntity;
import xyz.guqing.app.service.MenuService;
import xyz.guqing.app.utils.SecurityUserHelper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-05-29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('menu:view')")
    public ResultEntity<List<VueRouter<Menu>>> getMenu() {
        String username = SecurityUserHelper.getCurrentUsername();
        List<VueRouter<Menu>> userRouters = menuService.listUserRouters(username);
        return ResultEntity.ok(userRouters);
    }

    @GetMapping("router")
    @PreAuthorize("hasAuthority('menu:view')")
    public ResultEntity<List<MenuDTO>> getRouterList() {
        String username = SecurityUserHelper.getCurrentUsername();
        List<Menu> menus = menuService.listUserMenus(username);
        return ResultEntity.ok(convertTo(menus));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('menu:view')")
    public ResultEntity<Menu> getById(@PathVariable Long id) {
        return ResultEntity.ok(menuService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('menu:view')")
    public ResultEntity<List<MenuTree>> listMenuTree(MenuQuery menuQuery) {
        Menu menu = menuQuery.convertTo();
        List<MenuTree> menuTrees = this.menuService.listTreeMenus(menu);
        return ResultEntity.ok(menuTrees);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('menu:save')")
    @ControllerEndpoint(operation = "保存菜单/按钮", exceptionMessage = "保存菜单/按钮失败")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid MenuParam menuParam) {
        Menu menu = menuParam.convertTo();
        menuService.saveOrUpdate(menu);
        return ResultEntity.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('menu:delete')")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public ResultEntity<String> deleteMenus(@RequestBody List<Long> menuIds) {
        menuService.deleteMenus(menuIds);
        return ResultEntity.ok();
    }

    private List<MenuDTO> convertTo(List<Menu> menus) {
        return menus.stream()
                .map(menu -> (MenuDTO)new MenuDTO().convertFrom(menu))
                .collect(Collectors.toList());
    }
}
