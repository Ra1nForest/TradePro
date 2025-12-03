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
import java.math.BigDecimal;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("products")
@ApiModel(value = "Products对象", description = "商品表")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("商品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("商品描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("商品单价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品分类ID")
    @TableField("category_id")
    private Integer categoryId;

    @ApiModelProperty("商品数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty("仓库位置ID")
    @TableField("warehouse_location_id")
    private Integer warehouseLocationId;
}
