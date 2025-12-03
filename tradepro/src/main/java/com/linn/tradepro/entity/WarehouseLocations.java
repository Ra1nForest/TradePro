package com.linn.tradepro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 仓库位置表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("warehouse_locations")
@ApiModel(value = "WarehouseLocations对象", description = "仓库位置表")
public class WarehouseLocations implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("仓库位置ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("仓库ID")
    @TableField("warehouse_id")
    private Integer warehouseId;

    @ApiModelProperty("位置名称")
    @TableField("location")
    private String location;
}
