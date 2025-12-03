package com.linn.tradepro.common.entity;


import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.entity.Merchants;
import lombok.Data;

@Data
public class RegisterRequest {
    private Merchants merchants;
    private Accounts accounts;
}
