package xyz.guqing.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.ActionLogDTO;
import xyz.guqing.app.model.entity.ActionLog;
import xyz.guqing.app.model.params.ActionLogQuery;
import xyz.guqing.app.model.support.PageInfo;
import xyz.guqing.app.model.support.QueryRequest;
import xyz.guqing.app.model.support.ResultEntity;
import xyz.guqing.app.service.ActionLogService;

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
    public ResultEntity<PageInfo<ActionLogDTO>> list(ActionLogQuery actionLogQuery, QueryRequest queryRequest) {
        IPage<ActionLog> actionLogPage = actionLogService.listBy(actionLogQuery,queryRequest);
        return ResultEntity.okList(actionLogPage, actionLog -> new ActionLogDTO().convertFrom(actionLog));
    }
}
