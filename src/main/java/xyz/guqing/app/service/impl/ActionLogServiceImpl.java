package xyz.guqing.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.guqing.app.mapper.ActionLogMapper;
import xyz.guqing.app.model.entity.ActionLog;
import xyz.guqing.app.model.params.ActionLogQuery;
import xyz.guqing.app.model.support.QueryRequest;
import xyz.guqing.app.service.ActionLogService;
import xyz.guqing.app.utils.RegionAddressUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author guqing
 * @date 2020-06-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActionLogServiceImpl extends ServiceImpl<ActionLogMapper, ActionLog> implements ActionLogService {
    private final ObjectMapper objectMapper;

    @Override
    public void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation, String username, long start) {
        ActionLog actionLog = new ActionLog();
        actionLog.setIp(ip);

        actionLog.setUsername(username);
        actionLog.setExecutionTime(System.currentTimeMillis() - start);
        actionLog.setOperation(operation);

        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        actionLog.setMethod(className + "." + methodName + "()");

        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            actionLog.setParams(params.toString());
        }
        actionLog.setCreateTime(LocalDateTime.now());
        log.debug("访问ip的位置：[{}]", RegionAddressUtils.getCityInfo(ip));
        actionLog.setLocation(RegionAddressUtils.getCityInfo(ip));
        // 保存系统日志
        save(actionLog);
    }

    @Override
    public IPage<ActionLog> listBy(ActionLogQuery logQuery, QueryRequest queryRequest) {
        Long pageSize = queryRequest.getPageSize();
        Long current = queryRequest.getCurrent();

        LambdaQueryWrapper<ActionLog> queryWrapper = Wrappers.lambdaQuery();

        if(logQuery.getUsername() != null) {
            queryWrapper.like(ActionLog::getUsername, logQuery.getUsername());
        }

        if(logQuery.getOperation() != null) {
            queryWrapper.like(ActionLog::getOperation, logQuery.getOperation());
        }

        if(logQuery.getLocation() != null) {
            queryWrapper.like(ActionLog::getLocation, logQuery.getLocation());
        }

        // createFrom <= createTime <= createTo
        if(logQuery.getCreateFrom() != null) {
            queryWrapper.ge(ActionLog::getCreateTime, logQuery.getCreateFrom());
        }

        if(logQuery.getCreateTo() != null) {
            queryWrapper.le(ActionLog::getCreateTime, logQuery.getCreateTo());
        }

        // 按照创建时间降序排列，最近的显示在最前面
        queryWrapper.orderByDesc(ActionLog::getCreateTime);
        return page(new Page<>(current, pageSize), queryWrapper);
    }

    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
    }
}
