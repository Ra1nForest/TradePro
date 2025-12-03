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
 * 订单表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("orders")
@ApiModel(value = "Orders对象", description = "订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("商户操作账户ID")
    @TableField("merchant_account_id")
    private Integer merchantAccountId;

    @ApiModelProperty("合作伙伴商户ID")
    @TableField("partner_id")
    private Integer partnerId;

    @ApiModelProperty("合作伙伴商户操作账户ID")
    @TableField("partner_account_id")
    private Integer partnerAccountId;

    @ApiModelProperty("临时合作伙伴商户ID")
    @TableField("temporary_partners_id")
    private Integer temporaryPartnersId;

    @ApiModelProperty("订单金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("订单折扣")
    @TableField("order_discount")
    private Integer orderDiscount;

    @ApiModelProperty("订单细节/备注")
    @TableField("details")
    private String details;

    @ApiModelProperty("订单创建时间")
    @TableField("created_at")
    private String createdAt;

    @ApiModelProperty("订单确认时间")
    @TableField("complete_at")
    private String completeAt;
}
