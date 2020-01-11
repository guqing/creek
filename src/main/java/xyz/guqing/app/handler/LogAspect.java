package xyz.guqing.app.handler;

import com.alibaba.fastjson.JSONArray;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.guqing.app.model.annotation.WriteLog;
import xyz.guqing.app.model.entity.Log;
import xyz.guqing.app.model.entity.User;
import xyz.guqing.app.model.enums.LogType;
import xyz.guqing.app.security.support.MyUserDetails;
import xyz.guqing.app.security.utils.SecurityUserHelper;
import xyz.guqing.app.service.LogService;
import xyz.guqing.app.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 日志切面
 *
 * @author guqin
 * @date 2019-08-30 13:27
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	private HttpServletRequest request;
	private final LogService logService;
	private final UserService userService;

	@Autowired
	public LogAspect(HttpServletRequest request,
					 LogService logService,
					 UserService userService) {
		this.request = request;
		this.logService = logService;
		this.userService = userService;
	}

	@Pointcut("execution(* xyz.guqing.app.controller.*.*(..))")
	public void controllerAspect(){}

	@Pointcut("@annotation(xyz.guqing.app.model.annotation.WriteLog)")
	public void log() {}

	/**
	 *后置通知,在所有标注@WriteLog的地方切入
	 */
	@After("log()")
	public void doAfterAdvice(JoinPoint joinPoint){
		MethodSignature ms=(MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		// 记录日志信息
		insertLogInfo(method);
	}

	@AfterThrowing(value="controllerAspect()",throwing="e")
	public void  doAfterThrowing(JoinPoint joinPoint,Throwable e) throws Throwable {
		//获取请求的ip
		String ip = request.getRemoteAddr();
		Signature signature = joinPoint.getSignature();
		//异常方法
		String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName();
		//异常信息
		String errorInfo = e.getMessage();
		MethodSignature methodSignature = (MethodSignature) signature;
		// 获取参数名称
		String[] parameterNames = methodSignature.getParameterNames();
		String paramsName = JSONArray.toJSONString(parameterNames);

		logger.error("异常请求ip:{},异常方法全名:{},异常信息:{},参数名称:{}, 参数值:{}",
				ip,methodName,errorInfo,paramsName, joinPoint.getArgs());
	}

	private void insertLogInfo(Method method) {
		Log log = getLog(method);
		logService.saveLog(log);
	}

	private Log getLog(Method method) {
		MyUserDetails user = (MyUserDetails) SecurityUserHelper.getCurrentPrincipal();

		// 获取操作名(@log(name="内容"))
		WriteLog annotation = method.getAnnotation(WriteLog.class);
		LogType type = annotation.type();

		//获取request对象
		String ip = request.getRemoteAddr();
		Optional<User> userOptional = userService.findById(user.getId());
		Log log = new Log();
		userOptional.ifPresent(log::setUser);
		log.setMethodName(method.getName());
		log.setName(type.getName());
		log.setContent(annotation.value());
		log.setIp(ip);
		log.setType(type.getType());
		return log;
	}
}
