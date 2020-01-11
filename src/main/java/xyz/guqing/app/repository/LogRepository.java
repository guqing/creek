package xyz.guqing.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.guqing.app.model.entity.Log;

/**
 * @author guqing
 * @date 2020-01-11 17:59
 */
public interface LogRepository extends JpaRepository<Log, Long> {
}
