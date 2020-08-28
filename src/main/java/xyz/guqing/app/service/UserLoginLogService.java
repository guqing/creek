package xyz.guqing.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.app.model.entity.UserLoginLog;
import xyz.guqing.app.model.params.LoginLogParam;
import xyz.guqing.app.model.support.QueryRequest;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserLoginLogService extends IService<UserLoginLog> {
    /**
     * 根据条件分页查询用户登录日志
     * @param loginLogParam 查询参数
     * @param queryRequest 分页参数
     * @return 返回分页查询结果
     */
    IPage<UserLoginLog> listBy(LoginLogParam loginLogParam, QueryRequest queryRequest);
}
