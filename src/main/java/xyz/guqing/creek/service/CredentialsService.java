package xyz.guqing.creek.service;

import java.util.List;
import xyz.guqing.creek.model.dto.CredentialsDTO;

/**
 * @author guqing
 * @since 2022-01-14
 */
public interface CredentialsService {

    List<CredentialsDTO> listAll();
}
