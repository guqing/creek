package xyz.guqing.app.utils;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 分页结果对象
 * @author guqing
 * @date 2019-12-23 23:11
 */
@Data
public class PageInfo<DTO> {
    private List<DTO> list;
    private Long total;
    private Integer pages;
    private Integer current;
    private Integer pageSize;

    public static<T, DTO> PageInfo<DTO> convertFrom(Page<T> page, Function<T, DTO> function){
        List<T> content = page.getContent();
        PageInfo<DTO> pageInfo = getDtoPageInfo(function, content);
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setPages(page.getTotalPages());
        pageInfo.setCurrent(page.getNumber());
        pageInfo.setPageSize(page.getSize());
        return pageInfo;
    }

    public static<T, DTO> PageInfo<DTO> convertFrom(List<T> list, Function<T, DTO> function){
        Assert.isTrue(list != null, "The parameter of PageInfo can not be null.");
        PageInfo<DTO> pageInfo = getDtoPageInfo(function, list);
        int size = list.size();
        Long total = Long.parseLong(size + "");
        pageInfo.setTotal(total);
        pageInfo.setPages(1);
        pageInfo.setCurrent(1);
        pageInfo.setPageSize(1);
        return pageInfo;
    }

    private static <T, DTO> PageInfo<DTO> getDtoPageInfo(Function<T, DTO> function, List<T> content) {
        List<DTO> dtoList = new ArrayList<>();
        content.forEach(t -> {
            DTO dto = function.apply(t);
            dtoList.add(dto);
        });
        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(dtoList);
        return pageInfo;
    }
}
