package com.xyd.red_wine.order;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:38
 * @description:
 */

public class OrderModel implements Serializable{

    private List<MyOrderBean> my_order;

    public List<MyOrderBean> getMy_order() {
        return my_order;
    }

    public void setMy_order(List<MyOrderBean> my_order) {
        this.my_order = my_order;
    }

    public static class MyOrderBean implements Serializable{
        /**
         * order_num : 149502208915652833
         * num : 1
         * order_status : 4
         * g_img : /uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg
         * g_name : 测试商品
         * we_price : 0
         */

        private String order_num;
        private int num;
        private int order_status;
        private String g_img;
        private String g_name;
        private String price;

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        private int g_id;

        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }

        private String g_sname;

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String we_price) {
            this.price = we_price;
        }
    }
}
