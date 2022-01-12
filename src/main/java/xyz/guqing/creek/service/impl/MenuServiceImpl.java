package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.guqing.creek.mapper.MenuMapper;
import xyz.guqing.creek.model.bo.RouterMeta;
import xyz.guqing.creek.model.bo.VueRouter;
import xyz.guqing.creek.model.dto.MenuTree;
import xyz.guqing.creek.model.entity.Menu;
import xyz.guqing.creek.model.enums.MenuType;
import xyz.guqing.creek.service.MenuService;
import xyz.guqing.creek.service.RoleResourceService;
import xyz.guqing.creek.utils.TreeUtil;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private final RoleResourceService roleResourceService;

    @Override
    public List<Menu> listUserMenus(String username) {
        Set<Long> menuIds = roleResourceService.listMenuIdsByUsername(username);
        return listByIds(menuIds);
    }

    @Override
    public List<MenuTree> listTreeMenus(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getSortIndex);
        List<Menu> menus = baseMapper.selectList(queryWrapper);

        List<MenuTree> menuTrees = convertTo(menus);
        return TreeUtil.build(menuTrees);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenus(List<Long> menuIds) {
        delete(menuIds);
    }

    /**
     * 根据菜单id集合递归删除菜单
     * @param menuIds 菜单id集合
     */
    private void delete(List<Long> menuIds) {
        // 根据id集合删除菜单
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = list(queryWrapper);

        if (CollectionUtils.isEmpty(menus)) {
            return;
        }

        List<Long> menuIdList = menus.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        // 递归
        this.delete(menuIdList);
    }

    private List<MenuTree> convertTo(List<Menu> menus) {
        List<MenuTree> menuTrees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getId().toString());
            tree.setValue(tree.getId());
            tree.setKey(tree.getId());
            tree.setParentId(menu.getParentId().toString());
            tree.setTitle(menu.getTitle());
            tree.setIcon(menu.getIcon());
            menuTrees.add(tree);
        });
        return menuTrees;
    }

    @Override
    public List<VueRouter<Menu>> listUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.listUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            BeanUtils.copyProperties(menu, route);

            RouterMeta routerMeta = new RouterMeta();
            BeanUtils.copyProperties(menu, routerMeta);
            route.setMeta(routerMeta);

            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

}
