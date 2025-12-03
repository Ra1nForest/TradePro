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
 * 订单详情表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("order_details")
@ApiModel(value = "OrderDetails对象", description = "订单详情表")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单详情ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty("商品ID")
    @TableField("product_id")
    private Integer productId;

    @ApiModelProperty("商品数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty("商品折扣")
    @TableField("discount")
    private Integer discount;
}
