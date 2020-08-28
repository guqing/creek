package xyz.guqing.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.UserLoginLogDTO;
import xyz.guqing.app.model.entity.UserLoginLog;
import xyz.guqing.app.model.params.LoginLogParam;
import xyz.guqing.app.model.support.PageInfo;
import xyz.guqing.app.model.support.QueryRequest;
import xyz.guqing.app.model.support.ResultEntity;
import xyz.guqing.app.service.UserLoginLogService;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/ums/log/login")
@RequiredArgsConstructor
public class LoginLogController {
    private final UserLoginLogService userLoginLogService;

    @GetMapping
    public ResultEntity<PageInfo<UserLoginLogDTO>> list(LoginLogParam loginLogParam, QueryRequest queryRequest) {
        log.debug("登录日志列表参数:[{}]", queryRequest);
        IPage<UserLoginLog> userLoginLogs = userLoginLogService.listBy(loginLogParam,queryRequest);
        return ResultEntity.okList(userLoginLogs, userLoginLog -> new UserLoginLogDTO().convertFrom(userLoginLog));
    }
}
