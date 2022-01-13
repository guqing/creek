package xyz.guqing.creek.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import xyz.guqing.creek.model.dto.ApiResourceDTO;
import xyz.guqing.creek.model.entity.ApiResource;

/**
 * @author guqing
 * @sinceI2022-01-12
 */
public interface ApiResourceService extends IService<ApiResource> {

    List<ApiResourceDTO> listWithScopes();

}
