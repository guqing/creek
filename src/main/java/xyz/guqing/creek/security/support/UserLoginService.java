package xyz.guqing.creek.security.support;

import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.guqing.creek.config.WebSecurityConfig;
import xyz.guqing.creek.event.UserLoginEvent;
import xyz.guqing.creek.exception.AlreadyExistsException;
import xyz.guqing.creek.exception.AuthenticationException;
import xyz.guqing.creek.exception.BindSocialAccountException;
import xyz.guqing.creek.exception.NotFoundException;
import xyz.guqing.creek.model.bo.MyUserDetails;
import xyz.guqing.creek.model.constant.CreekConstant;
import xyz.guqing.creek.model.dto.SocialLoginDTO;
import xyz.guqing.creek.model.entity.User;
import xyz.guqing.creek.model.entity.UserConnection;
import xyz.guqing.creek.model.entity.UserRole;
import xyz.guqing.creek.model.enums.GenderEnum;
import xyz.guqing.creek.model.enums.UserStatusEnum;
import xyz.guqing.creek.model.params.BindUserParam;
import xyz.guqing.creek.security.model.AccessToken;
import xyz.guqing.creek.security.utils.JwtTokenUtils;
import xyz.guqing.creek.service.UserConnectionService;
import xyz.guqing.creek.service.UserRoleService;
import xyz.guqing.creek.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final ApplicationContext applicationContext;
    private final AuthRequestFactory factory;
    private final UserService userService;
    private final UserConnectionService userConnectionService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final JwtTokenUtils jwtTokenUtils;

    private final WebSecurityConfig webSecurityConfig;

    public AccessToken login(String username, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
        Authentication authenticate = webSecurityConfig.getAuthenticationManager().authenticate(authRequest);
        MyUserDetails userDetails = (MyUserDetails)authenticate.getPrincipal();

        // 推送登录成功时间
        applicationContext.publishEvent(new UserLoginEvent(this, userDetails.getUsername()));
        return jwtTokenUtils.generateAccessToken(userDetails.getUsername());
    }

    public SocialLoginDTO resolveLogin(String type, AuthCallback callback) {
        AuthUser authUser = getAuthUserFromCallback(type, callback);
        String source = authUser.getSource();
        UserConnection userConnection = userConnectionService.getBySourceAndUuid(source, authUser.getUuid());

        SocialLoginDTO socialLoginDTO = new SocialLoginDTO();
        if(Objects.isNull(userConnection)) {
            socialLoginDTO.setIsBind(false);
            socialLoginDTO.setAuthUser(authUser);
            return socialLoginDTO;
        }

        User user = userService.getById(userConnection.getUserId());
        socialLoginDTO.setIsBind(true);
        socialLoginDTO.setAccessToken(jwtTokenUtils.generateToken(user));

        // 发送登录成功时间
        applicationContext.publishEvent(new UserLoginEvent(this, user.getUsername()));
        return socialLoginDTO;
    }

    public AuthRequest getAuthRequest(String type) {
        if (StringUtils.isNotBlank(type)) {
            return factory.get(type);
        } else {
            throw new AuthenticationException(String.format("暂不支持%s第三方登录", type));
        }
    }

    /**
     * 注册并登录
     *
     * @param registerUser 注册用户
     * @return 注册并登录成功返回令牌对象
     */
    @Transactional(rollbackFor = Exception.class)
    public AccessToken socialSignLogin(BindUserParam registerUser) {
        // 校验验证码
        Optional<User> optionalUser = userService.getByEmail(registerUser.getEmail());
        if(optionalUser.isPresent()) {
            // 用户存在，则抛出异常
            throw new AlreadyExistsException("该用户已经存在");
        }

        AuthUser authUser = registerUser.getSocialUserParam().convertTo();
        String encryptPassword = passwordEncoder.encode(registerUser.getPassword());
        // 注册
        User patinaUser = authUserPatina(registerUser.getEmail(), encryptPassword, authUser);
        User user = registerUser(patinaUser);
        user.setNickname(authUser.getNickname());
        user.setDescription(authUser.getRemark());
        user.setAvatar(authUser.getAvatar());
        // 保存第三方绑定帐号
        userConnectionService.create(user.getId(), authUser);
        // 发送登录成功事件
        applicationContext.publishEvent(new UserLoginEvent(this, user.getUsername()));
        return jwtTokenUtils.generateAccessToken(user.getUsername());
    }

    /**
     * 注册用户
     *
     * @param user 填充好信息的用户
     * @return 返回保存后填充了id的用户
     */
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(User user) {
        userService.save(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        // 注册用户角色 ID
        userRole.setRoleId(CreekConstant.REGISTER_ROLE_ID);
        userRoleService.save(userRole);
        return user;
    }

    /**
     * 使用第三方用户信息对毛坯用户润色，让其信息更加光鲜
     * @param email 邮箱地址
     * @param encryptPassword 加密后的密码
     * @param authUser 第三方登录获取的用户信息
     * @return 返回润色后的用户信息对象
     */
    private User authUserPatina(String email, String encryptPassword, AuthUser authUser) {
        User user = shapingBaseUser(email, encryptPassword);
        if(StringUtils.isNotBlank(authUser.getNickname())) {
            user.setNickname(authUser.getNickname());
        }
        user.setAvatar(authUser.getAvatar());
        user.setDescription(authUser.getRemark());
        if(StringUtils.isNotBlank(authUser.getUsername())) {
            user.setUsername(authUser.getUsername() + authUser.getUuid());
        }
        return user;
    }

    /**
     * 塑造一个毛坯用户，填充了基础信息
     * @param email 电子邮件
     * @param encryptPassword 加密后的密码
     * @return 返回填充了信息的用户
     */
    private User shapingBaseUser(String email, String encryptPassword) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(email);
        user.setUsername(generateUsername());
        user.setPassword(encryptPassword);
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setGender(GenderEnum.UNKNOWN.name());
        user.setAvatar("");
        user.setDescription("这个用户很懒，什么也没有留下");
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(LocalDateTime.now());
        return user;
    }

    private String generateUsername() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public void bind(String username, AuthUser authUser) {
        String source = authUser.getSource();
        UserConnection userConnection = userConnectionService.getBySourceAndUuid(source, authUser.getUuid());
        if (userConnection != null) {
            throw new BindSocialAccountException("绑定失败，该第三方账号已被绑定,请先解绑后重试");
        }
        User user = userService.getByUsername(username);
        if(user != null) {
            userConnectionService.create(user.getId(), authUser);
        }
    }

    public SocialLoginDTO resolveBind(String oauthType, AuthCallback callback) {
        AuthUser authUser = getAuthUserFromCallback(oauthType, callback);
        SocialLoginDTO socialLoginDTO = new SocialLoginDTO();
        socialLoginDTO.setIsBind(false);
        socialLoginDTO.setAuthUser(authUser);
        return socialLoginDTO;
    }

    private AuthUser getAuthUserFromCallback(String oauthType, AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest(oauthType);
        AuthResponse response = authRequest.login(callback);
        if (!response.ok()) {
            throw new AuthenticationException("第三方登录失败:" + response.getMsg());
        }
        return (AuthUser) response.getData();
    }

    public List<String> listProviderByUsername(String username) {
        Long userId = getUserIdByUsername(username);

        List<UserConnection> userConnections = userConnectionService.listByUserId(userId);
        if(CollectionUtils.isEmpty(userConnections)) {
            return Collections.emptyList();
        }

        return userConnections.stream().map(UserConnection::getProviderName).collect(Collectors.toList());
    }

    public void unbind(String username, String oauthType) {
        Long userId = getUserIdByUsername(username);
        userConnectionService.deleteBy(userId, oauthType);
    }

    private Long getUserIdByUsername(String username) {
        User user = userService.getByUsername(username);
        if(user == null) {
            throw new NotFoundException("用户不存在");
        }
        return user.getId();
    }
}
