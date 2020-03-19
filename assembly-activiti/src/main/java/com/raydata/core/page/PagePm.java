package com.raydata.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class PagePm<T> implements IPage<T> {
    private static final long serialVersionUID = 8545996863226528798L;

    private long page = 1;
    private long limit = 10;
    private long totalResult = 0;
    private List<T> currentResult = Collections.emptyList();


    /**
     * 查询数据列表
     */
//    private List<T> records = Collections.emptyList();

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    public PagePm() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PagePm(long current, long size) {
        this(current, size, 0);
    }

    public PagePm(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PagePm(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public PagePm(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.page = current;
        }
        this.limit = size;
        this.totalResult = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.page > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.page < this.getPages();
    }

    @Override
    @JsonIgnore
    public List<T> getRecords() {
        return this.currentResult;
    }

    @Override
    public PagePm<T> setRecords(List<T> records) {
        this.currentResult = records;
        return this;
    }

    @Override
    @JsonIgnore
    public long getTotal() {
        return this.page;
    }

    @Override
    public PagePm<T> setTotal(long total) {
        this.totalResult = total;
        return this;
    }

    @Override
    @JsonIgnore
    public long getSize() {
        return this.limit;
    }

    @Override
    public PagePm<T> setSize(long size) {
        this.limit = size;
        return this;
    }

    @Override
    @JsonIgnore
    public long getCurrent() {
        return this.page;
    }

    @Override
    public PagePm<T> setCurrent(long current) {
        this.page = current;
        return this;
    }

    /**
     * 获取当前正序排列的字段集合
     * <p>
     * 为了兼容，将在不久后废弃
     *
     * @return 正序排列的字段集合
     * @see #getOrders()
     * @deprecated 3.2.0
     */
    @Override
    @Nullable
    @Deprecated
    public String[] ascs() {
        return CollectionUtils.isNotEmpty(orders) ? mapOrderToArray(OrderItem::isAsc) : null;
    }

    /**
     * 查找 order 中正序排序的字段数组
     *
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PagePm<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PagePm<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    /**
     * 设置需要进行正序排序的字段
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param ascs 字段
     * @return 返回自身
     * @deprecated 3.2.0
     */
    @Deprecated
    public PagePm<T> setAscs(List<String> ascs) {
        return CollectionUtils.isNotEmpty(ascs) ? setAsc(ascs.toArray(new String[0])) : this;
    }

    /**
     * 升序
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param ascs 多个升序字段
     * @deprecated 3.2.0
     */
    @Deprecated
    public PagePm<T> setAsc(String... ascs) {
        // 保证原来方法 set 的语意
        removeOrder(OrderItem::isAsc);
        for (String s : ascs) {
            addOrder(OrderItem.asc(s));
        }
        return this;
    }

    /**
     * 获取需简要倒序排列的字段数组
     * <p>
     *
     * @return 倒序排列的字段数组
     * @see #getOrders()
     * @deprecated 3.2.0
     */
    @Override
    @Deprecated
    public String[] descs() {
        return mapOrderToArray(i -> !i.isAsc());
    }

    /**
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param descs 需要倒序排列的字段
     * @return 自身
     * @deprecated 3.2.0
     */
    @Deprecated
    public PagePm<T> setDescs(List<String> descs) {
        // 保证原来方法 set 的语意
        if (CollectionUtils.isNotEmpty(descs)) {
            removeOrder(item -> !item.isAsc());
            for (String s : descs) {
                addOrder(OrderItem.desc(s));
            }
        }
        return this;
    }

    /**
     * 降序，这方法名不知道是谁起的
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param descs 多个降序字段
     * @deprecated 3.2.0
     */
    @Deprecated
    public PagePm<T> setDesc(String... descs) {
        setDescs(Arrays.asList(descs));
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return getOrders();
    }

    @JsonIgnore
    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        if (totalResult < 0) {
            return false;
        }
        return isSearchCount;
    }

    public PagePm<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PagePm<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }


    //===================

    public long getPage() {
        return page;
    }


    public long getLimit() {
        return limit;
    }


    public long getTotalResult() {
        return totalResult;
    }


    public List<T> getCurrentResult() {
        return currentResult;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }
}
