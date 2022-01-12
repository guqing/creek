package xyz.guqing.creek.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.creek.model.annotation.ControllerEndpoint;
import xyz.guqing.creek.model.dto.RoleDTO;
import xyz.guqing.creek.model.entity.Role;
import xyz.guqing.creek.model.params.RoleParam;
import xyz.guqing.creek.model.params.RoleQuery;
import xyz.guqing.creek.model.support.PageInfo;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.RoleService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/options")
    @PreAuthorize("hasAuthority('read:role')")
    public ResultEntity<List<RoleDTO>> listAll() {
        List<Role> list = roleService.list();
        return ResultEntity.ok(convertTo(list));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('read:role')")
    public ResultEntity<PageInfo<RoleDTO>> listBy(RoleQuery roleQuery, PageQuery pageQuery) {
        roleQuery.setPageQuery(pageQuery);
        log.debug("角色查询对象：{}", JSONObject.toJSONString(roleQuery));
        Page<Role> pageInfo = roleService.listBy(roleQuery);
        return ResultEntity.okList(pageInfo, role -> new RoleDTO().convertFrom(role));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:role')")
    public ResultEntity<RoleDTO> get(@PathVariable Long id) {
        return ResultEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('write:role')")
    @ControllerEndpoint(operation = "保存/更新角色", exceptionMessage = "保存/更新角色失败")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid RoleParam roleParam) {
        Role role = roleParam.convertTo();
        roleService.createOrUpdate(role, roleParam.getMenuIds());
        return ResultEntity.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('delete:role')")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public ResultEntity<String> deleteRoles(@RequestBody List<Long> roleIds) {
        roleService.deleteRoles(roleIds);
        return ResultEntity.ok();
    }

    private List<RoleDTO> convertTo(List<Role> list) {
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream()
            .map(role -> (RoleDTO)new RoleDTO().convertFrom(role))
            .collect(Collectors.toList());
    }
}
