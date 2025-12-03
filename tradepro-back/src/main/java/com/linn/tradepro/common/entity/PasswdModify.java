package com.linn.tradepro.common.entity;

import lombok.Data;

@Data
public class PasswdModify {
    private Integer id;
    private String oldPasswd;
    private String newPasswd;
    private String confirmPasswd;
}
