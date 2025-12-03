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
 * 临时合作伙伴表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("temporary_partners")
@ApiModel(value = "TemporaryPartners对象", description = "临时合作伙伴表")
public class TemporaryPartners implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("临时合作伙伴ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("商户名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("联系人姓名")
    @TableField("contact_name")
    private String contactName;

    @ApiModelProperty("电话号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("伙伴商户状态")
    @TableField("status")
    private String status;
}
