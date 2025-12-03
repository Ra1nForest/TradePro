package com.linn.tradepro.common.entity;

import lombok.Data;

/**
 * 分页查询请求参数封装
 */

@Data
public class PageRequest {
    private int pageNum = 1; // 当前页码
    private int pageSize = 10; // 每页显示的记录数
    private String keyword; // 查询条件
}