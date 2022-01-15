package xyz.guqing.creek.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.guqing.creek.mapper.CredentialsMapper;
import xyz.guqing.creek.model.dto.CredentialsDTO;
import xyz.guqing.creek.model.entity.Credentials;
import xyz.guqing.creek.service.CredentialsService;

/**
 * @author guqing
 * @since 2022-01-14
 */
@Service
@AllArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsMapper credentialsMapper;

    @Override
    public List<CredentialsDTO> listAll() {
        return credentialsMapper.selectList(Wrappers.lambdaQuery(Credentials.class)
                .orderByDesc(Credentials::getCreateTime))
            .stream()
            .map(credentials -> (CredentialsDTO) new CredentialsDTO().convertFrom(credentials))
            .collect(Collectors.toList());
    }
}
