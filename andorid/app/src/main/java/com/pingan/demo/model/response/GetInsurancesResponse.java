package com.pingan.demo.model.response;

import com.pingan.http.framework.model.response.BaseResponse;

import java.util.List;

/**
 * Created by guolidong752 on 17/3/14.
 */

public class GetInsurancesResponse extends BaseResponse {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * "id": "ee143a534a62189f266eb224959f485977e3ddbd0e73d9c637e44ef89986a886",
         * "name": "name",
         * "description": "description",
         * "amount_max": 0,
         * "age_max": 0,
         * "count_bought": 3,
         * "count_helped": 3,
         * "balance": 1925,
         * "count_new_current": 1,
         * "count_quit_current": 0,
         * "count_helped_current": 0,
         * "amount_current": 123,
         * "cost_current": 123,
         * "count_helped_next": 10,
         * "amount_next": 1948,
         * "cost_next": 123,
         * "fee_next": 0.68,
         * "claims": [
         * {
         * "id_insurance": "ee143a534a62189f266eb224959f485977e3ddbd0e73d9c637e44ef89986a886",
         * "id_user": "username",
         * "id_claim": "e8b01cd95f44169c701e0ddd6e72f2f95d400e51570e287af0c7da3dcaf622c6",
         * "name": "zhangsan",
         * "mobile": "13800138000",
         * "cardnum": "542313233",
         * "status": "claimed",
         * "amount": 0
         * }
         */

        private String id;//保险编号
        private String name;//保险名称
        private String time_founded;//成立时间
        private List<String> description;//保险描述(数组，列表？10个字符串)
//        private String description;//保险描述／
        private int amount_max;//最大保额
        private int age_max;//年龄限制
        private int count_bought;//参与人数
        private int count_helped;//资助人数
        private double balance;//资金池余额
        private int count_new_current;//本月新增人数
        private int count_quit_current;//本月减少人数
        private int count_helped_current;//本月资助人数
        private double amount_current;//本月资助金额
        private double cost_current;//本月运营成本
        private int count_helped_next;//下月资助人数
        private double amount_next;//下月资助金额
        private double cost_next;//下月运营成本
        private double fee_next;//下月分摊

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }

        public int getAmount_max() {
            return amount_max;
        }

        public void setAmount_max(int amount_max) {
            this.amount_max = amount_max;
        }

        public int getAge_max() {
            return age_max;
        }

        public void setAge_max(int age_max) {
            this.age_max = age_max;
        }

        public int getCount_bought() {
            return count_bought;
        }

        public void setCount_bought(int count_bought) {
            this.count_bought = count_bought;
        }

        public int getCount_helped() {
            return count_helped;
        }

        public void setCount_helped(int count_helped) {
            this.count_helped = count_helped;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public int getCount_new_current() {
            return count_new_current;
        }

        public void setCount_new_current(int count_new_current) {
            this.count_new_current = count_new_current;
        }

        public int getCount_quit_current() {
            return count_quit_current;
        }

        public void setCount_quit_current(int count_quit_current) {
            this.count_quit_current = count_quit_current;
        }

        public int getCount_helped_current() {
            return count_helped_current;
        }

        public void setCount_helped_current(int count_helped_current) {
            this.count_helped_current = count_helped_current;
        }

        public double getAmount_current() {
            return amount_current;
        }

        public void setAmount_current(double amount_current) {
            this.amount_current = amount_current;
        }

        public double getCost_current() {
            return cost_current;
        }

        public void setCost_current(double cost_current) {
            this.cost_current = cost_current;
        }

        public int getCount_helped_next() {
            return count_helped_next;
        }

        public void setCount_helped_next(int count_helped_next) {
            this.count_helped_next = count_helped_next;
        }

        public double getAmount_next() {
            return amount_next;
        }

        public void setAmount_next(double amount_next) {
            this.amount_next = amount_next;
        }

        public double getCost_next() {
            return cost_next;
        }

        public void setCost_next(double cost_next) {
            this.cost_next = cost_next;
        }

        public double getFee_next() {
            return fee_next;
        }

        public void setFee_next(double fee_next) {
            this.fee_next = fee_next;
        }

        public String getTime_founded() {
            return time_founded;
        }

        public void setTime_founded(String time_founded) {
            this.time_founded = time_founded;
        }

        public List<String> getDescription() {
            return description;
        }

        public void setDescription(List<String> description) {
            this.description = description;
        }
    }
}
