package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.service.UserService;
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

    @Autowired
    public DashboardController(RoleService roleService,
                               UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
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
}
