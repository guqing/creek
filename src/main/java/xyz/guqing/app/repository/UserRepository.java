package xyz.guqing.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.guqing.app.model.entity.User;

/**
 * @author guqing
 * @date 2019-12-21 20:11
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username=?1 AND u.deleted=?2 AND u.status=?3")
    User findByUsername(String username, Integer deleted, Integer status);

    @Query("select u from User u where u.email=?1 AND u.deleted=?2 AND u.status=?3")
    User findByEmail(String email, Integer deleted, Integer status);
}
