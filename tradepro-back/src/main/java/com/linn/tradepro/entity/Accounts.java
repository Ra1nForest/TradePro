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
 * 子账号表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("accounts")
@ApiModel(value = "Accounts对象", description = "子账号表")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("子账号ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户ID")
    @TableField("merchant_id")
    private Integer merchantId;

    @ApiModelProperty("账号用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("账号密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("账号拥有者姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("账号拥有者电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("账号权限角色")
    @TableField("role")
    private String role;
}
