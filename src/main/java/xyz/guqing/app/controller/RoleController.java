package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.RoleDTO;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.service.RoleService;
import xyz.guqing.app.utils.PageInfo;
import xyz.guqing.app.utils.Result;


/**
 * @author guqing
 * @date 2020-01-04 19:37
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public Result<PageInfo<RoleDTO>> list(@RequestParam(defaultValue = "1") Integer current,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Role> roles = roleService.findAllByPage(current, pageSize);
        return Result.okList(roles, role -> new RoleDTO().convertFrom(role));
    }

    @GetMapping("/count")
    public Result count() {
        Long roleCount = roleService.count();
        return Result.ok(roleCount);
    }
}
