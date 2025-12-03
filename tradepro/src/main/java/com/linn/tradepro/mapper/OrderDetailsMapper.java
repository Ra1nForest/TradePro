package com.linn.tradepro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linn.tradepro.entity.OrderDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单详情表 Mapper 接口
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Mapper
public interface OrderDetailsMapper extends BaseMapper<OrderDetails> {

}
