package com.sunflower.bulma.tools;

import com.google.common.base.Preconditions;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.sunflower.bulma.tools.consts.PageConstant.*;


/**
 * @author fuyongde
 * @desc 内存分页的工具类
 * @date 2017/11/15 13:52
 */
public final class PageUtils {

    private static final String PAGE_INDEX_ERROR = "Current pageIndex is %s, it must greater or equal to %s";
    private static final String PAGE_SIZE_ERROR = "Current pageSize is %s, it must greater or equal to %s";
    private static final String COUNT_ERROR = "Current count is %s, it must greater or equal to %s";

    private PageUtils() {
    }

    /**
     * 对原始数据进行拆分
     *
     * @param list     原始数据
     * @param pageSize 页面大小
     * @param <T>      任何类型
     * @return 分页后的数据
     */
    private static <T> List<List<T>> split(@NonNull List<T> list, int pageSize) {

        Preconditions.checkArgument(pageSize >= PAGE_SIZE_MIN, PAGE_SIZE_ERROR, pageSize, PAGE_SIZE_MIN);

        //获取总记录数
        int totalSize = list.size();

        //获取总页数
        int totalPage = (totalSize + pageSize - 1) / pageSize;

        //创建一个为总页数大小的list
        List<List<T>> result = new ArrayList<>(totalPage);

        //将数据分页之后放入结果集
        for (int i = 0, next = i * pageSize; i < totalPage; i++) {
            result.add(new ArrayList<>(list.subList(next, (i < totalPage - 1) ? (next = next + pageSize) : totalSize)));
        }

        return result;
    }

    /**
     * 内存分页取数据
     *
     * @param list      原始数据
     * @param pageIndex 页码
     * @param pageSize  页面大小
     * @param <T>       任何类型
     * @return 当页的数据，当页码超出最大页码时，返回null
     */
    public static <T> List<T> getByPage(@NonNull List<T> list, int pageIndex, int pageSize) {
        Preconditions.checkArgument(pageIndex >= PAGE_INDEX_MIN, PAGE_INDEX_ERROR, pageIndex, PAGE_INDEX_MIN);
        int totalPage = totalPage(list.size(), pageSize);
        return pageIndex >= totalPage ? null : split(list, pageSize).get(pageIndex);
    }

    /**
     * 计算总页数
     *
     * @param count    总数
     * @param pageSize 页面大小
     * @return 总页数
     */
    public static int totalPage(int count, int pageSize) {
        Preconditions.checkArgument(count >= COUNT_MIN, COUNT_ERROR, pageSize, COUNT_MIN);
        Preconditions.checkArgument(pageSize >= PAGE_SIZE_MIN, PAGE_SIZE_ERROR, pageSize, PAGE_SIZE_MIN);
        return (count + pageSize - 1) / pageSize;
    }

    /**
     * 是否有下一页
     *
     * @param count     总记录数
     * @param pageIndex 当前页面
     * @param pageSize  页面大小
     * @return true=有下一页|false=没有下一页
     */
    public static boolean hasMore(int count, int pageIndex, int pageSize) {
        int totalPage = totalPage(count, pageSize);
        Preconditions.checkArgument(pageIndex >= PAGE_INDEX_MIN, PAGE_INDEX_ERROR, pageIndex, PAGE_INDEX_MIN);
        return (pageIndex + 1) < totalPage;
    }

    /**
     * 获取开始数量
     *
     * @param pageIndex 开始页码
     * @param pageSize  页码大小
     * @return 开始的值
     */
    public static int getStart(int pageIndex, int pageSize) {
        Preconditions.checkArgument(pageIndex >= PAGE_INDEX_MIN, PAGE_INDEX_ERROR, pageIndex, PAGE_INDEX_MIN);
        Preconditions.checkArgument(pageSize >= PAGE_SIZE_MIN, PAGE_SIZE_ERROR, pageSize, PAGE_SIZE_MIN);
        return pageIndex * pageSize;
    }
}
