package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.WarehouseLocations;
import com.linn.tradepro.mapper.WarehouseLocationsMapper;
import com.linn.tradepro.service.WarehouseLocationsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓库位置表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class WarehouseLocationsServiceImpl extends ServiceImpl<WarehouseLocationsMapper, WarehouseLocations> implements WarehouseLocationsService {

    @Resource
    private WarehouseLocationsMapper warehouseLocationsMapper;

    @Override
    public List<WarehouseLocations> searchByWarehouseId(Integer id) {
        try{
            QueryWrapper<WarehouseLocations> wrapper = new QueryWrapper<>();
            wrapper.eq("warehouse_id", id);
            return warehouseLocationsMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
