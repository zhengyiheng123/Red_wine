package com.xyd.red_wine.winedetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 10:44
 * @description:
 */

public class WineModel implements Serializable{


    /**
     * good : {"g_id":2,"g_name":"测试商品","g_price":"620.00","g_num":66,"g_store":0,"g_con":"<p>小试牛刀<\/p>","g_img":"/uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg"}
     * comments : [{"head_img":"/uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg","nickname":"孤独音乐人","content":"","pubtime":"","img":"","star":0},{"head_img":"/uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg","nickname":"孤独音乐人","content":"湿哒哒","pubtime":"1495081844","img":"","star":0},{"head_img":"","nickname":"cW5I9q","content":"不错不错","pubtime":"1494917831","img":"","star":4},{"head_img":"","nickname":"cW5I9q","content":"撒网群多群无多群","pubtime":"1494828050","img":"","star":4},{"head_img":"","nickname":"cW5I9q","content":"不错，有需要还会再次来的，祝店家生意兴隆。","pubtime":"1494826875","img":"","star":4},{"head_img":"/uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg","nickname":"孤独音乐人","content":"嗯嗯嗯嗯嗯嗯嗯","pubtime":"1494327638","img":"","star":0},{"head_img":"","nickname":"cW5I9q","content":"搭","pubtime":"1493952055","img":"","star":0},{"head_img":"","nickname":"cW5I9q","content":"qwe","pubtime":"1493951220","img":"/data/wwwroot/www/www.jiangliping.com/public/uploads/20170505/e304bec9af61687be2bc4fdedf417ba4.gif,","star":3},{"head_img":"/uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg","nickname":"孤独音乐人","content":"不错","pubtime":"1493950965","img":"/data/wwwroot/www/www.jiangliping.com/public/uploads/20170505/adc71a5ec07557878b5545379591719f.gif,","star":4},{"head_img":"/uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg","nickname":"孤独音乐人","content":"不错","pubtime":"1493950742","img":"/data/wwwroot/www/www.jiangliping.com/public/uploads/20170505/ca1087ae315b6f0c5664b9605fc3e55d.gif,","star":0}]
     */

    private GoodBean good;

    private String g_con;

    public String getG_con() {
        return g_con;
    }

    public void setG_con(String g_con) {
        this.g_con = g_con;
    }
    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }



    public static class GoodBean implements Serializable{
        /**
         * g_id : 2
         * g_name : 测试商品
         * g_price : 620.00
         * g_num : 66
         * g_store : 0
         * g_con : <p>小试牛刀</p>
         * g_img : /uploads/20170707/c4bccd975eca57178bab849ca8feb1e8.jpg
         */
        private String g_sname;
        private String g_img;
        private int g_id;
        private String g_name;
        private double g_price;
        private int g_num;
        private int g_store;

        public String getG_kind() {
            return g_kind;
        }

        public void setG_kind(String g_kind) {
            this.g_kind = g_kind;
        }

        public String getG_freight() {
            return g_freight;
        }

        public void setG_freight(String g_freight) {
            this.g_freight = g_freight;
        }

        private String g_kind;   //商品类型
        private String g_freight;   //商品运费
        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }



        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public double getG_price() {
            return g_price;
        }

        public void setG_price(double g_price) {
            this.g_price = g_price;
        }

        public int getG_num() {
            return g_num;
        }

        public void setG_num(int g_num) {
            this.g_num = g_num;
        }

        public int getG_store() {
            return g_store;
        }

        public void setG_store(int g_store) {
            this.g_store = g_store;
        }



        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }
    }


}
