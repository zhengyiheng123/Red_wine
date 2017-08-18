package com.xyd.red_wine.logistics;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 10:49
 * @description:
 */

public class LogisticsModel {

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * orders : {"order_num":"149507912842959080","express":"3903520223691","postcom":"yunda"}
     * check : [{"time":"2017-04-06 11:43:34","ftime":"2017-04-06 11:43:34","context":"[北京丰台区富丰园公司]快件已被 已签收 签收","location":"北京丰台区富丰园公司"},{"time":"2017-04-06 08:23:11","ftime":"2017-04-06 08:23:11","context":"[北京丰台区富丰园公司]进行派件扫描；派送业务员：万晨阳；联系电话：17718455009","location":"北京丰台区富丰园公司"},{"time":"2017-04-05 22:10:42","ftime":"2017-04-05 22:10:42","context":"[北京分拨中心]从站点发出，本次转运目的地：北京丰台区富丰园公司","location":"北京分拨中心"},{"time":"2017-04-05 20:54:03","ftime":"2017-04-05 20:54:03","context":"[北京分拨中心]在分拨中心进行卸车扫描","location":"北京分拨中心"},{"time":"2017-04-04 21:08:44","ftime":"2017-04-04 21:08:44","context":"[浙江杭州分拨中心]进行装车扫描，即将发往：北京分拨中心","location":"浙江杭州分拨中心"},{"time":"2017-04-04 21:07:51","ftime":"2017-04-04 21:07:51","context":"[浙江杭州分拨中心]在分拨中心进行称重扫描","location":"浙江杭州分拨中心"},{"time":"2017-04-04 20:01:42","ftime":"2017-04-04 20:01:42","context":"[浙江杭州萧山区城北公司]进行下级地点扫描，将发往：北京网点包","location":"浙江杭州萧山区城北公司"},{"time":"2017-04-04 19:09:34","ftime":"2017-04-04 19:09:34","context":"[浙江杭州萧山区城北公司]进行揽件扫描","location":"浙江杭州萧山区城北公司"}]
     */
private String state;
    private OrdersBean orders;
    private List<CheckBean> check;

    public OrdersBean getOrders() {
        return orders;
    }

    public void setOrders(OrdersBean orders) {
        this.orders = orders;
    }

    public List<CheckBean> getCheck() {
        return check;
    }

    public void setCheck(List<CheckBean> check) {
        this.check = check;
    }

    public static class OrdersBean {
        /**
         * order_num : 149507912842959080
         * express : 3903520223691
         * postcom : yunda
         */

        private String order_num;
        private String express;
        private String postcom;

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
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
    }

    public static class CheckBean {
        /**
         * time : 2017-04-06 11:43:34
         * ftime : 2017-04-06 11:43:34
         * context : [北京丰台区富丰园公司]快件已被 已签收 签收
         * location : 北京丰台区富丰园公司
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
