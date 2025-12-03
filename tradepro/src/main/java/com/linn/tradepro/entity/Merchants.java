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
 * 商户表
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("merchants")
@ApiModel(value = "Merchants对象", description = "商户表")
public class Merchants implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("联系人")
    @TableField("contact_person")
    private String contactPerson;

    @ApiModelProperty("联系人电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("电子邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("操作密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private String createdAt;
}
