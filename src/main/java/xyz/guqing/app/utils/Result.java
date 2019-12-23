package xyz.guqing.app.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author guqing
 * @date 2019/8/9
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static Result<String> ok() {
        return new Result<String>(0, "成功", "");
    }

    public static<T> Result<T> ok(T data) {
        return new Result<T>(0, "成功", data);
    }

    public static<T> Result<PageInfo<T>> okList(Page<T> page) {
        PageInfo<T> pageInfo = PageInfo.convertTo(page);
        return ok(pageInfo);
    }

    public static<T> Result okList(List<T> list) {
        return ok(PageInfo.convertTo(list));
    }

    public static Result<String> fail() {
        return new Result<>(-1, "错误", "");
    }

    public static<T> Result<T> fail(int errno, String message, T data) {
        return new Result<>(errno, message, data);
    }

    public static Result<String> fail(int errno, String message) {
        return fail(errno, message, "");
    }

    public static Result badArgument() {
        return fail(401, "参数不对");
    }

    public static Result badArgument(Map<String, String> validMap) {
        return fail(401, "参数不对", validMap);
    }

    public static Result badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Result businessError(String message) {
        return fail(500, message);
    }

    public static Result unLogin() {
        return fail(501, "请登录");
    }

    public static Result serious() {
        return fail(502, "系统内部错误");
    }

    public static Result unSupport() {
        return fail(503, "业务不支持");
    }

    public static Result updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Result updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Result unauthorized() {
        // 没有操作权限即未授权
        return fail(506, "无操作权限");
    }

    public static Result RPCFailed() {
        return fail(507, "远程调用失败");
    }

    public static Result repeatOps() {
        return fail(508, "重复操作");
    }
}
