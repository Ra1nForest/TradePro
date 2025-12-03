package com.linn.tradepro.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<T> {
    private long pageNum = 1; // 当前页码
    private long pageSize = 10; //    每页显示的记录数
    private long total; // 总记录数
    private long pages; // 总页数
    private List<T> data = new ArrayList<>(); // 当前页的数据列表

    public PageResult(long pageNum, long pageSize, long total, long pages, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.data = data;
    }

    public PageResult(IPage<T> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.data = page.getRecords();
    }
}
