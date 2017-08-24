package com.xyd.red_wine.balance;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/15
 * @time: 16:47
 * @description:  提现充值记录
 */

public class CashValueModel {

    private List<CashValueBean> cash_value;

    public List<CashValueBean> getCash_value() {
        return cash_value;
    }

    public void setCash_value(List<CashValueBean> cash_value) {
        this.cash_value = cash_value;
    }

    public static class CashValueBean {
        /**
         * rl_id : 3
         * u_id : 1
         * recharge_num : 15020964301217223
         * recharge_money : 0.00
         * we_price : 0.01
         * pay_type : 2
         * rl_state : 2
         * create_time : 1502096430
         * update_time : 1502157833
         * pay_account :
         * rl_type
         */
        private int rl_type;
        private int rl_id;
        private int u_id;
        private String recharge_num;
        private String recharge_money;
        private String we_price;
        private int pay_type;
        private int rl_state;
        private long create_time;
        private long update_time;
        private String pay_account;

        public int getRl_type() {
            return rl_type;
        }

        public void setRl_type(int rl_type) {
            this.rl_type = rl_type;
        }

        public int getRl_id() {
            return rl_id;
        }

        public void setRl_id(int rl_id) {
            this.rl_id = rl_id;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public String getRecharge_num() {
            return recharge_num;
        }

        public void setRecharge_num(String recharge_num) {
            this.recharge_num = recharge_num;
        }

        public String getRecharge_money() {
            return recharge_money;
        }

        public void setRecharge_money(String recharge_money) {
            this.recharge_money = recharge_money;
        }

        public String getWe_price() {
            return we_price;
        }

        public void setWe_price(String we_price) {
            this.we_price = we_price;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getRl_state() {
            return rl_state;
        }

        public void setRl_state(int rl_state) {
            this.rl_state = rl_state;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getPay_account() {
            return pay_account;
        }

        public void setPay_account(String pay_account) {
            this.pay_account = pay_account;
        }
    }
}
