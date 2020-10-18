package xyz.guqing.creek.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.creek.mapper.UserLoginLogMapper;
import xyz.guqing.creek.model.entity.UserLoginLog;
import xyz.guqing.creek.model.params.LoginLogParam;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.service.UserLoginLogService;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Slf4j
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {
    @Override
    public IPage<UserLoginLog> listBy(LoginLogParam loginLogParam, PageQuery pageQuery) {
        log.debug("列表查询参数:{}", JSONObject.toJSONString(loginLogParam));

        LambdaQueryWrapper<UserLoginLog> queryWrapper = Wrappers.lambdaQuery();

        Long current = pageQuery.getCurrent();
        Long pageSize = pageQuery.getPageSize();

        String username = loginLogParam.getUsername();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(UserLoginLog::getUsername, username);
        }

        LocalDateTime createFrom = loginLogParam.getCreateFrom();
        LocalDateTime createTo = loginLogParam.getCreateTo();
        if (createFrom != null && createTo != null) {
            queryWrapper.ge(UserLoginLog::getLoginTime, createFrom)
                    .le(UserLoginLog::getLoginTime, createTo);
        }

        queryWrapper.orderByDesc(UserLoginLog::getLoginTime);
        return page(new Page<>(current, pageSize), queryWrapper);
    }
}
