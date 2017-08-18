package com.xyd.red_wine.commissionorder;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:07
 * @description:
 */

public class CommissionOrderModel {

    /**
     * total_revenue : 2.00
     * deduct : [{"g_name":"测试商品","g_price":"620.00","g_num":66,"g_img":"/uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg","give_money":"12.00","addtime":0,"lev":0,"nickname":"KCvT1l","head_img":""},{"g_name":"测试商品","g_price":"620.00","g_num":66,"g_img":"/uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg","give_money":"21.00","addtime":0,"lev":0,"nickname":"rkGIax","head_img":""}]
     */

    private String total_revenue;
    private List<DeductBean> deduct;

    public String getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(String total_revenue) {
        this.total_revenue = total_revenue;
    }

    public List<DeductBean> getDeduct() {
        return deduct;
    }

    public void setDeduct(List<DeductBean> deduct) {
        this.deduct = deduct;
    }

    public static class DeductBean {
        /**
         * g_name : 测试商品
         * g_price : 620.00
         * g_num : 66
         * g_img : /uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg
         * give_money : 12.00
         * addtime : 0
         * lev : 0
         * nickname : KCvT1l
         * head_img :
         */

        private String g_name;
        private String g_price;
        private int g_num;
        private String g_img;
        private String give_money;
        private long addtime;
        private int lev;

        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }

        private String g_sname;
        private String nickname;
        private String head_img;

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

        public String getGive_money() {
            return give_money;
        }

        public void setGive_money(String give_money) {
            this.give_money = give_money;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public int getLev() {
            return lev;
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
