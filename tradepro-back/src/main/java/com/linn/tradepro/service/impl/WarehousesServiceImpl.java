package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.Warehouses;
import com.linn.tradepro.mapper.WarehousesMapper;
import com.linn.tradepro.service.WarehouseLocationsService;
import com.linn.tradepro.service.WarehousesService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class WarehousesServiceImpl extends ServiceImpl<WarehousesMapper, Warehouses> implements WarehousesService {

    @Resource
    private WarehousesMapper warehousesMapper;

    @Resource
    private WarehouseLocationsService warehouseLocationsService;

    @Override
    public boolean save(Warehouses warehouses) {
        Subject subject = org.apache.shiro.SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getPrincipal();
        if (warehouses.getId() == null) {
            warehouses.setMerchantId(merchants.getId());
            return warehousesMapper.insert(warehouses) > 0;
        } else {
            if (!Objects.equals(warehouses.getMerchantId(), merchants.getId())) {
                return false;
            }
            return warehousesMapper.updateById(warehouses) > 0;
        }
    }

    @Override
    public boolean delete(Warehouses warehouses) {
        Subject subject = org.apache.shiro.SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getPrincipal();
        if (!Objects.equals(warehouses.getMerchantId(), merchants.getId()))
            return false;
        if (warehouseLocationsService.searchByWarehouseId(warehouses.getId()).size() == 0)
            return warehousesMapper.deleteById(warehouses.getId()) > 0;
        else
            return false;
    }

    @Override
    public List<Warehouses> searchAll() {
        Subject subject = SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getPrincipal();
        QueryWrapper<Warehouses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", merchants.getId());
        return warehousesMapper.selectList(queryWrapper);
    }
}
