package xyz.guqing.creek.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import xyz.guqing.creek.model.constant.CreekConstant;
import xyz.guqing.creek.model.support.PageQuery;

/**
 * mybatis plus 分页和排序工具类
 * @author guqing
 * @date 2020-09-01
 */
public class PageUtils {
    private PageUtils(){}

    public static<T> Page<T> convert(PageQuery pageQuery) {
        Page<T> page = new Page<>();

        String sortField = pageQuery.getField();
        if(StringUtils.isNotBlank(sortField)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(CreekConstant.ORDER_ASC.equalsIgnoreCase(pageQuery.getOrder()));
            // 驼峰转下划线
            String underscoreField = CreekUtils.camelToUnderscore(sortField);
            orderItem.setColumn(underscoreField);
            page.addOrder(orderItem);
        }

        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        return page;
    }
}
