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
 * 仓库表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("warehouses")
@ApiModel(value = "Warehouses对象", description = "仓库表")
public class Warehouses implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("仓库ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("仓库名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("仓库地址")
    @TableField("address")
    private String address;
}
