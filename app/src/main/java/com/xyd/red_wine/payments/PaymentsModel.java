package com.xyd.red_wine.payments;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/11
 * @time: 10:33
 * @description:
 */

public class PaymentsModel implements Serializable{


    /**
     *  chest : 捐赠总额
     *   welfare_list :公益列表
     */

    private String chest;
    private List<WelfareListBean> welfare_list;

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public List<WelfareListBean> getWelfare_list() {
        return welfare_list;
    }

    public void setWelfare_list(List<WelfareListBean> welfare_list) {
        this.welfare_list = welfare_list;
    }

    public static class WelfareListBean {
        /**
         * g_name : 商品名称
         * g_price : 商品价格
         * g_num : 商品数量
         * g_img : 商品图片
         * donate_money : 捐赠金额
         * create_time : 捐赠时间
         */

        private String g_name;
        private String g_price;
        private int g_num;
        private String g_img;
        private String donate_money;
        private String create_time;

        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }

        private String g_sname;

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getG_price() {
            return g_price;
        }

        public void setG_price(String g_price) {
            this.g_price = g_price;
        }

        public int getG_num() {
            return g_num;
        }

        public void setG_num(int g_num) {
            this.g_num = g_num;
        }

        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }

        public String getDonate_money() {
            return donate_money;
        }

        public void setDonate_money(String donate_money) {
            this.donate_money = donate_money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
