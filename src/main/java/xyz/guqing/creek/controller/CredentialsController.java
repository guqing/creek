package xyz.guqing.creek.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.creek.model.dto.CredentialsDTO;
import xyz.guqing.creek.model.entity.Credentials;
import xyz.guqing.creek.model.params.CredentialsParam;
import xyz.guqing.creek.model.support.ResultEntity;
import xyz.guqing.creek.service.CredentialsService;

/**
 * @author guqing
 * @since 2022-01-14
 */
@RestController
@RequestMapping("/credentials")
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping
    public ResultEntity<List<CredentialsDTO>> list() {
        List<CredentialsDTO> results = credentialsService.listAll();
        return ResultEntity.ok(results);
    }

    @PostMapping
    public ResultEntity<String> create(@RequestBody @Valid CredentialsParam param) {
        Credentials credentials = param.convertTo();
        return null;
    }
}
