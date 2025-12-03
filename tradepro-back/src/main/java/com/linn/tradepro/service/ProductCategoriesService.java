package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.ProductCategories;

import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface ProductCategoriesService extends IService<ProductCategories> {
    boolean save(ProductCategories productCategories);
    boolean delete(ProductCategories productCategories);
    List<ProductCategories> searchAll();

}
