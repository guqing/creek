package xyz.guqing.creek.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.creek.model.dto.ActionLogDTO;
import xyz.guqing.creek.model.entity.ActionLog;
import xyz.guqing.creek.model.params.ActionLogQuery;
import xyz.guqing.creek.model.support.PageInfo;
import xyz.guqing.creek.model.support.PageQuery;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.ActionLogService;

/**
 * @author guqing
 * @date 2020-07-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log/action")
public class ActionLogController {
    private final ActionLogService actionLogService;

    @GetMapping
    public ResultEntity<PageInfo<ActionLogDTO>> list(ActionLogQuery actionLogQuery, PageQuery pageQuery) {
        IPage<ActionLog> actionLogPage = actionLogService.listBy(actionLogQuery,pageQuery);
        return ResultEntity.okList(actionLogPage, actionLog -> new ActionLogDTO().convertFrom(actionLog));
    }
}
