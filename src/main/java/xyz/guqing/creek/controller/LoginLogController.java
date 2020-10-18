package xyz.guqing.creek.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.creek.model.dto.UserLoginLogDTO;
import xyz.guqing.creek.model.entity.UserLoginLog;
import xyz.guqing.creek.model.params.LoginLogParam;
import xyz.guqing.creek.model.support.PageInfo;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.UserLoginLogService;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/log/login")
@RequiredArgsConstructor
public class LoginLogController {
    private final UserLoginLogService userLoginLogService;

    @GetMapping
    public ResultEntity<PageInfo<UserLoginLogDTO>> list(LoginLogParam loginLogParam, PageQuery pageQuery) {
        log.debug("登录日志列表参数:[{}]", pageQuery);
        IPage<UserLoginLog> userLoginLogs = userLoginLogService.listBy(loginLogParam,pageQuery);
        return ResultEntity.okList(userLoginLogs, userLoginLog -> new UserLoginLogDTO().convertFrom(userLoginLog));
    }
}
