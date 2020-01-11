package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.LogDTO;
import xyz.guqing.app.model.entity.Log;
import xyz.guqing.app.service.LogService;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.service.UserService;
import xyz.guqing.app.utils.PageInfo;
import xyz.guqing.app.utils.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-01-05 18:59
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final RoleService roleService;
    private final UserService userService;
    private final LogService logService;

    @Autowired
    public DashboardController(RoleService roleService,
                               UserService userService,
                               LogService logService) {
        this.roleService = roleService;
        this.userService = userService;
        this.logService = logService;
    }
    @GetMapping("/count-ram")
    public Result countRam() {
        Map<String, Long> map = new HashMap<>(4);
        Long roleCount = roleService.count();
        map.put("roleCount", roleCount);

        Long userCount = userService.count();
        map.put("userCount", userCount);
        return Result.ok(map);
    }

    @RequestMapping("/log/list")
    public Result<PageInfo<LogDTO>> listByPage(@RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Log> logs = logService.findByPage(current, pageSize);
        return Result.okList(logs, log -> new LogDTO().convertFrom(log));
    }
}
