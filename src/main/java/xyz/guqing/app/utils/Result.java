package xyz.guqing.app.utils;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guqing
 * @date 2019/8/9
 */
public class Result<T> {
    private Map<String, Object> result;

    private Result(Map<String, Object> result) {
        this.result = result;
    }

    public static Result ok() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("message", "成功");
        return new Result(result);
    }

    public static<T> Result ok(T data) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("code", 0);
        result.put("message", "成功");
        result.put("data", data);
        return new Result(result);
    }

    public static<T> Result okList(Page<T> page) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", page.getContent());

        data.put("total", page.getTotalElements());
        data.put("pages", page.getTotalPages());
        data.put("page", page.getNumber());
        data.put("limit", page.getSize());

        return new Result(data);
    }

    public static<T> Result okList(List<T> list) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("code", 0);
        result.put("message", "成功");
        result.put("list", list);
        return new Result(result);
    }

    public static Result fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", -1);
        obj.put("message", "错误");
        return new Result(obj);
    }

    public static Result fail(int errno, String message) {
        Map<String, Object> obj = new HashMap<String, Object>(16);
        obj.put("code", errno);
        obj.put("message", message);
        return new Result(obj);
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object unLogin() {
        return fail(501, "请登录");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unSupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }

    public static Object RPCFailed() {
        return fail(507, "远程调用失败");
    }

    public static Object repeatOps() {
        return fail(508, "重复操作");
    }
}
