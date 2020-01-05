package xyz.guqing.app.service;

import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.guqing.app.exception.AuthFailException;
import xyz.guqing.app.exception.ServiceException;
import xyz.guqing.app.model.dto.PermissionDTO;
import xyz.guqing.app.model.dto.RoleDTO;
import xyz.guqing.app.model.dto.UserDTO;
import xyz.guqing.app.model.entity.Permission;
import xyz.guqing.app.model.entity.Role;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.entity.UserConnect;
import xyz.guqing.app.model.params.LoginParam;
import xyz.guqing.app.model.params.OauthUserParam;
import xyz.guqing.app.model.support.LoginTypeConstant;
import xyz.guqing.app.model.support.UserConnectConverter;
import xyz.guqing.app.model.support.UserStatusConstant;
import xyz.guqing.app.repository.UserConnectRepository;
import xyz.guqing.app.repository.UserRepository;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.utils.IpUtil;
import xyz.guqing.app.security.utils.JwtTokenUtil;
import xyz.guqing.app.utils.Result;

import java.util.*;
import java.util.Optional;

/**
 * @author guqing
 * @date 2019/8/9
 */
@Service
@CacheConfig(cacheNames = "userService")
public class UserService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final UserConnectRepository userConnectRepository;

    @Autowired
    public UserService(JwtTokenUtil jwtTokenUtil,
                       UserRepository userRepository,
                       PermissionService permissionService,
                       UserConnectRepository userConnectRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.userConnectRepository = userConnectRepository;
    }

    @Cacheable(key = "#username", unless = "#result==null")
    public User getUserByUsername(String username, Integer loginType){
        if(loginType == LoginTypeConstant.EMAIL) {
            return userRepository.findByEmail(username, UserStatusConstant.NORMAL, UserStatusConstant.NORMAL);
        } else if(loginType == LoginTypeConstant.USERNAME){
            return userRepository.findByUsername(username, UserStatusConstant.NORMAL, UserStatusConstant.NORMAL);
        }

        return null;
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息DTO对象
     */
    @Cacheable(unless = "#result==null")
	public UserDTO getUserInfo(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Assert.isTrue(userOptional.isPresent(), "用户不存在");
        User user = userOptional.get();
        Role role = user.getRole();
        List<Permission> permissions = role.getPermissions();
        return userDtoConverter(user, role, permissions);
    }

    /**
     * 更新用户的最后登录时间
     * @param userId 用户id
     * @param ip 用户登录ip
     */
    public void updateLoginTime(Integer userId, String ip) {
        Optional<User> userOptional = userRepository.findById(userId);
        Assert.isTrue(userOptional.isPresent(), "用户不存在");
        User user = userOptional.get();
        user.setId(userId);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(ip);
        userRepository.save(user);
    }

    private UserDTO userDtoConverter(User user, Role role, List<Permission> permissions) {
        UserDTO userDTO = new UserDTO().convertFrom(user);
        //设置角色和权限
        RoleDTO roleDTO = new RoleDTO().convertFrom(role);
        userDTO.setRole(roleDTO);
        userDTO.setRoleId(roleDTO.getName());

        List<PermissionDTO> permissionDtoList = new ArrayList<>();
        permissions.forEach(permission -> {
            PermissionDTO permissionDTO = new PermissionDTO().convertFrom(permission);
            permissionDtoList.add(permissionDTO);
        });

        roleDTO.setPermissions(permissionDtoList);
        return userDTO;
    }

    /**
     * 用户登录
     * @param loginParam 登录入数
     * @param userDetails 用户信息
     * @param ip 登录ip
     * @return 如果登录成功返回token，否则返回null
     */
    public String login(LoginParam loginParam, MyUserDetails userDetails, String ip) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordEqual = bCryptPasswordEncoder.matches(loginParam.getPassword(), userDetails.getPassword());;
        if(!passwordEqual) {
            throw new AuthFailException("用户密码不正确");
        }
        // 颁发token
        String token = jwtTokenUtil.generateToken(userDetails);
        // 更新用户最后登录时间和ip
        updateLoginTime(userDetails.getId(), ip);
        return token;
    }

    /**
     * 第三方登录功能
     * @param response 第三方登录响应对象
     * @param ip 用户的ip地址
     * @return 登录成功返回token，如果登录失败抛出AuthFailException异常由全局统一处理
     */
    @Transactional(rollbackFor = ServiceException.class)
    public String oauthLogin(AuthResponse response, String ip) {
        AuthUser authUser = (AuthUser)response.getData();
        UserConnect userConnect = UserConnectConverter.convertTo(authUser, ip);
        System.out.println(userConnect);
        if(userConnect.getAccessToken() == null) {
            // 登录失败
            throw new AuthFailException("第三方授权登录失败");
        }

        // 登录成功
        Optional<UserConnect> userConnectOptional = userConnectRepository.findByUuidAndProviderId(userConnect.getUuid(), userConnect.getProviderId());
        // 判断数据库是存在一条UserConnect的记录
        if(userConnectOptional.isPresent()) {
            UserConnect userConnectModel = userConnectOptional.get();
            User user = userConnectModel.getUser();
            return jwtTokenUtil.generateToken(user);
        } else {
            // 登录成功但是数据库没有UserConnect记录保存一条,并默认根据user connect新增用户，然后颁发token
            User user = userRepository.save(userConnect.getUser());
            userConnect.setUser(user);
            UserConnect newUserConnectRecord = userConnectRepository.save(userConnect);
            return jwtTokenUtil.generateToken(newUserConnectRecord.getUser());
        }
    }

    public Page<User> findAllByPage(Integer current, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(current, pageSize));
    }

    public Long count() {
        return userRepository.count();
    }
}
