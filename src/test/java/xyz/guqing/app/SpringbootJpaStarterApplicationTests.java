package xyz.guqing.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.repository.RoleRepository;
import xyz.guqing.app.service.RoleService;

@SpringBootTest
class SpringbootJpaStarterApplicationTests {
@Autowired
private RoleRepository roleRepository;
    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        boolean matches = encoder.matches( "123456", "$2a$10$xNOU4iD86Fa4tyrwrM1z1ug/WokuFHyQzgPKiTkctOMukuDBdCHZq");
        System.out.println(matches);
    }

}
