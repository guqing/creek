package xyz.guqing.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.guqing.app.model.entity.UserConnect;

import java.util.Optional;

/**
 * @author guqing
 * @date 2019-12-24 22:56
 */
public interface UserConnectRepository extends JpaRepository<UserConnect, Integer> {
    /**
     * 根据uuid和provideId查询UserConnect
     * @param uuid 第三方登录唯一标识
     * @param providerId 第三方登录提供商名称
     * @return 返回UserConnect的Optional对象
     */
    Optional<UserConnect> findByUuidAndProviderId(String uuid, String providerId);
}
