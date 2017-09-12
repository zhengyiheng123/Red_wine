package com.xyd.red_wine.orderdetail;

import java.io.Serializable;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/29
 * @time: 17:11
 * @description:
 */

public class OrderDetailModel implements Serializable {

    /**
     * order_num : 15033868221004361401
     * num : 1
     * content :
     * price : 799.00
     * we_price : 0.01
     * order_status : 3
     * express : 70027151930509
     * postcom : 百世汇通
     * create_time : 1503386822
     * update_time : 1503390274
     * give_time : 1503390274
     * pay_type : 1
     * a_name : ding
     * a_address : Ereggggdddd
     * link_phone : 17600882423
     * g_name : 庞特卡奈古堡红葡萄酒
     * g_sname : Chateau Pontet-Canet, Pauillac, France
     * g_price : 799.00
     * g_img : /uploads/20170726/ebd4eeb95ca2e6e843203a5bada3ea68.jpg
     * g_kind : 750ml
     * g_freight : 0.00
     * logistics_information : {"time":"2017-07-23 17:43:12","ftime":"2017-07-23 17:43:12","context":"北京市|签收|北京市【北京丰台区富丰分部】，陈 已签收","location":""}
     */

    private String order_num;
    private int num;
    private String content;
    private String price;
    private String we_price;
    private int order_status;
    private String express;
    private String postcom;
    private long create_time;
    private long update_time;
    private long give_time;
    private int pay_type;
    private String a_name;
    private String a_address;
    private String link_phone;
    private String g_name;
    private String g_sname;
    private String g_price;
    private String g_img;
    private String g_kind;
    private String g_freight;

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    private int g_id;
    private LogisticsInformationBean logistics_information;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWe_price() {
        return we_price;
    }

    public void setWe_price(String we_price) {
        this.we_price = we_price;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getPostcom() {
        return postcom;
    }

    public void setPostcom(String postcom) {
        this.postcom = postcom;
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

    public long getGive_time() {
        return give_time;
    }

    public void setGive_time(long give_time) {
        this.give_time = give_time;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_address() {
        return a_address;
    }

    public void setA_address(String a_address) {
        this.a_address = a_address;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_sname() {
        return g_sname;
    }

    public void setG_sname(String g_sname) {
        this.g_sname = g_sname;
    }

    public String getG_price() {
        return g_price;
    }

    public void setG_price(String g_price) {
        this.g_price = g_price;
    }

    public String getG_img() {
        return g_img;
    }

    public void setG_img(String g_img) {
        this.g_img = g_img;
    }

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

    public LogisticsInformationBean getLogistics_information() {
        return logistics_information;
    }

    public void setLogistics_information(LogisticsInformationBean logistics_information) {
        this.logistics_information = logistics_information;
    }

    public static class LogisticsInformationBean {
        /**
         * time : 2017-07-23 17:43:12
         * ftime : 2017-07-23 17:43:12
         * context : 北京市|签收|北京市【北京丰台区富丰分部】，陈 已签收
         * location :
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
