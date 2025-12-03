package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.ProductCategories;
import com.linn.tradepro.mapper.ProductCategoriesMapper;
import com.linn.tradepro.service.ProductCategoriesService;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class ProductCategoriesServiceImpl extends ServiceImpl<ProductCategoriesMapper, ProductCategories> implements ProductCategoriesService {

    @Resource
    private ProductCategoriesMapper productCategoriesMapper;

    @Override
    public boolean save(ProductCategories productCategories) {
        try {
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<ProductCategories> wrapper = new QueryWrapper<>();
            wrapper.eq("id", productCategories.getId());
            ProductCategories pc = productCategoriesMapper.selectOne(wrapper);
            if (pc == null){
                productCategories.setMerchantId(merchants.getId());
                return productCategoriesMapper.insert(productCategories) > 0;
            } else {
                if (!pc.getMerchantId().equals(merchants.getId()))
                    return false;
                return productCategoriesMapper.updateById(productCategories) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(ProductCategories productCategories) {
        try{
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (!productCategories.getMerchantId().equals(merchants.getId()))
                return false;
            return productCategoriesMapper.deleteById(productCategories) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductCategories> searchAll() {
        try{
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<ProductCategories> wrapper = new QueryWrapper<>();
            wrapper.eq("merchant_id", merchants.getId());
            return productCategoriesMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
