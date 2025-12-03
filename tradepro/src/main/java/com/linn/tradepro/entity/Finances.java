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
import java.time.LocalDateTime;

/**
 * <p>
 * 财务表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("finances")
@ApiModel(value = "Finances对象", description = "财务表")
public class Finances implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("财务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty("交易类型：收入、支出")
    @TableField("transaction_type")
    private String transactionType;

    @ApiModelProperty("交易金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("交易描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private String createdAt;
}
