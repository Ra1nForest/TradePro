package com.linn.tradepro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linn.tradepro.entity.Accounts;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 子账号表 Mapper 接口
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Mapper
public interface AccountsMapper extends BaseMapper<Accounts> {

}
