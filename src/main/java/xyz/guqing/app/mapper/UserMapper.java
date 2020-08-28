package xyz.guqing.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.app.model.bo.CurrentUser;
import xyz.guqing.app.model.entity.User;

import java.util.Optional;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回用户信息的Optional对象
     */
    Optional<CurrentUser> findByUsername(String username);
}
