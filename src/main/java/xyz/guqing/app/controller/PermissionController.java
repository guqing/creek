package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.PermissionDTO;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.service.PermissionService;
import xyz.guqing.app.utils.PageInfo;
import xyz.guqing.app.utils.Result;

import java.util.List;

/**
 * @author guqing
 * @date 2020-01-10 20:46
 */
@RestController()
@RequestMapping("/permission")
public class PermissionController {
    private final PermissionService permissionService;
    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/listAll")
    public Result<PageInfo<PermissionDTO>> listAll() {
        List<Permission> permissions = permissionService.findAll();
        return Result.okList(permissions, permission -> new PermissionDTO().convertFrom(permission));
    }
}
