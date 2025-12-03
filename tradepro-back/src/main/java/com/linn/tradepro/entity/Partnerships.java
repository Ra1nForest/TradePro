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
 * 正式合作伙伴表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("partnerships")
@ApiModel(value = "Partnerships对象", description = "正式合作伙伴表")
public class Partnerships implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关系ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("伙伴商户ID")
    @TableField("partnership_id")
    private Integer partnershipId;

    @ApiModelProperty("伙伴商户状态")
    @TableField("status")
    private String status;
}
