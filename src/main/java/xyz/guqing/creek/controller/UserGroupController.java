package xyz.guqing.creek.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.creek.model.annotation.ControllerEndpoint;
import xyz.guqing.creek.model.dto.UserGroupTree;
import xyz.guqing.creek.model.entity.UserGroup;
import xyz.guqing.creek.model.params.UserGroupParam;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.UserGroupService;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-05
 */
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('read:group')")
    public ResultEntity<List<UserGroupTree>> list(String name) {
        List<UserGroupTree> userGroupTrees = userGroupService.listBy(name);
        return ResultEntity.ok(userGroupTrees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:group')")
    public ResultEntity<UserGroup> getById(@PathVariable Long id) {
        return  ResultEntity.ok(userGroupService.getById(id));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('write:group')")
    @ControllerEndpoint(operation = "保存用户组", exceptionMessage = "保存用户组失败")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid UserGroupParam userGroupParam) {
        UserGroup userGroup = userGroupParam.convertTo();
        userGroupService.createOrUpdate(userGroup);
        return ResultEntity.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('delete:group')")
    @ControllerEndpoint(operation = "删除用户组", exceptionMessage = "删除用户组失败")
    public ResultEntity<String> delete(List<Long> groupIds) {
        userGroupService.removeByIds(groupIds);
        return ResultEntity.ok();
    }
}
