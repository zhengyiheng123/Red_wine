package com.xyd.red_wine.store;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:27
 * @description:
 */

public class StoreModel {

    private List<GoodsBean> goods;

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * g_id : 2
         * g_img : /uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg
         * g_price : 620.00
         * g_name : 测试商品
         */

        private int g_id;
        private String g_img;
        private String g_price;
        private String g_name;

        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }

        private String g_sname;

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }

        public String getG_price() {
            return g_price;
        }

        public void setG_price(String g_price) {
            this.g_price = g_price;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }
    }
}
