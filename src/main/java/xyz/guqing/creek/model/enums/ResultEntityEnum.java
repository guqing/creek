package xyz.guqing.creek.model.enums;

/**
 * @author guqing
 * @date 2020-04-15 10:24
 */
public enum ResultEntityEnum {
    /**
     * 一些返回值枚举
     */
    SUCCESS("00000", "成功"),
    USERNAME_CONFLICT("A0110", "用户名已经存在"),
    USER_PASSWORD_ERROR("A0120", "用户密码不正确"),
    USER_LOGIN_EXCEPTION("A0200", "用户登录异常"),
    USER_RESOURCE_EXCEPTION("A0600", "用户资源异常"),
    USER_NOT_FOUND("A0201", "用户账户不存在"),
    CERTIFICATE_VERIFICATION_FAILED("A0220", "用户身份校验失败"),
    UNAUTHORIZED("A0300", "未经授权,无操作权限"),
    ACCESS_DENIED("A0320", "用户访问被拒绝"),
    BAD_ARGUMENT("A0400", "用户请求参数错误"),
    BAD_ARGUMENT_VALUE("A0410", "用户必填参数为空"),
    USER_UPLOAD_ERROR("A0700", "用户上传文件异常"),
    UN_SUPPORTED_MEDIA_TYPE("A0701", "不支持的媒体类型"),
    SERVER_ERROR("B0001", "系统执行出错"),
    SERVER_EXECUTION_TIMEOUT("B0100", "系统执行超时"),
    RPC_FAILED("C0001", "调用第三方服务出错"),
    RPC_TIMEOUT("C0200", "第三方系统执行超时"),
    NOTIFICATION_ERROR("C0500", "通知服务出错"),
    REPEAT_OPS("A0506", "用户重复请求");


    private String code;
    private String desc;

    ResultEntityEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
