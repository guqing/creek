package xyz.guqing.app.utils;

/**
 * redis工具类
 *
 * @author guqing
 * @date 2020-04-20 10:57
 */
public class RedisUtils {
    /**
     * 主数据系统标识
     */
    private static final String KEY_PREFIX = "violet";
    /**
     * 分割字符，默认[:]，使用:可用于rdm分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * redis的key键规则定义
     *
     * @param module  模块名称
     * @param service service名称
     * @param args    参数..
     * @return key
     */
    public static String keyBuilder(String module, String service, String... args) {
        return keyBuilder(null, module, service, args);
    }

    /**
     * redis的key键规则定义
     *
     * @param module  模块名称
     * @param service service名称
     * @param objStr  对象.toString()
     * @return key
     */
    public static String keyBuilder(String module, String service, String objStr) {
        return keyBuilder(null, module, service, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     *
     * @param prefix  项目前缀
     * @param module  模块名称
     * @param service service名称
     * @param objStr  对象.toString()
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String service, String objStr) {
        return keyBuilder(prefix, module, service, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     *
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func   方法名称
     * @param args   参数..
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String... args) {
        // 项目前缀
        if (prefix == null) {
            prefix = KEY_PREFIX;
        }
        StringBuilder key = new StringBuilder(prefix);
        // KEY_SPLIT_CHAR 为分割字符
        key.append(KEY_SPLIT_CHAR).append(module).append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }
}
