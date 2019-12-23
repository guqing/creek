package xyz.guqing.app.utils;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 分页结果对象
 * @author guqing
 * @date 2019-12-23 23:11
 */
@Data
public class PageInfo<T> {
    private List<T> list;
    private Long total;
    private Integer pages;
    private Integer current;
    private Integer pageSize;

    public static<T> PageInfo<T> convertTo(Page<T> page) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(page.getContent());
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setPages(page.getTotalPages());
        pageInfo.setCurrent(page.getNumber());
        pageInfo.setPageSize(page.getSize());
        return pageInfo;
    }

    public static<T> PageInfo<T> convertTo(List<T> list) {
        Assert.isTrue(list != null, "The parameter of PageInfo can not be null.");
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        int size = list.size();
        Long total = Long.parseLong(size + "");
        pageInfo.setTotal(total);
        pageInfo.setPages(1);
        pageInfo.setCurrent(1);
        pageInfo.setPageSize(1);
        return pageInfo;
    }
}
