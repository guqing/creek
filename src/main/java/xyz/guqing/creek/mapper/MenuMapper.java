package xyz.guqing.creek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.creek.model.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询用户权限集合
     * @param username 用户名
     * @return 如果查询到返回权限集合否则返回空集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> findUserMenus(String username);
}
