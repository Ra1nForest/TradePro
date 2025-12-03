package com.linn.tradepro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linn.tradepro.entity.Finances;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 财务表 Mapper 接口
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Mapper
public interface FinancesMapper extends BaseMapper<Finances> {

}
