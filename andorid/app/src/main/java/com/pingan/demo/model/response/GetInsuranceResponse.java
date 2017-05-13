package com.pingan.demo.model.response;

import com.pingan.http.framework.model.response.BaseResponse;

/**
 * Created by guolidong752 on 16/1/3.
 */

public class GetInsuranceResponse extends BaseResponse {
//        "id": "6a3f39d07b65844ccd077a8d4a74f41f22fe4236a1d03fc77a13792d3e97719d",
//            "name": "name",
//            "description": "description",
//            "count_bought": 2,
//            "count_helped": 2,
//            "balance": 2048,
//            "cost_current": 123,
//            "count_helped_next": 10,
//            "amount_next": 1948,
//            "cost_next": 123,
//            "fee_next": 0.98
    public String id;//保险编号
    public String name;//保险名称
    public String description;//保险描述
    public int count_bought;//参与人数
    public int count_helped;//资助人数
    public double balance;//资金池余额
    public double cost_current;//本月运营成本
    public int count_helped_next;//下月资助人数
    public double amount_next;//下月资助金额
    public int cost_next;//下月运营成本
    public float fee_next;//下月分摊
}
