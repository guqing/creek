package xyz.guqing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.app.model.dto.LogDTO;
import xyz.guqing.app.model.entity.Log;
import xyz.guqing.app.service.LogService;
import xyz.guqing.app.utils.PageInfo;
import xyz.guqing.app.utils.Result;

/**
 * @author guqing
 * @date 2020-01-11 18:05
 */
@RestController
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @RequestMapping("/list")
    public Result<PageInfo<LogDTO>> listByPage(@RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Log> logs = logService.findByPage(current, pageSize);
        return Result.okList(logs, log -> new LogDTO().convertFrom(log));
    }
}
