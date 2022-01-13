package xyz.guqing.creek.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.creek.model.dto.ApiResourceDTO;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.ApiResourceService;

/**
 * @author guqing
 * @since 2022-01-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/resources")
public class ApiResourceController {
    private final ApiResourceService apiResourceService;

    @GetMapping("/list-view")
    public ResultEntity<List<ApiResourceDTO>> list() {
        List<ApiResourceDTO> apiResources = apiResourceService.listWithScopes();
        return ResultEntity.ok(apiResources);
    }
}
