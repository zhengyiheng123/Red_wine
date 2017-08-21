package com.xyd.red_wine.message;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:48
 * @description:
 */

public class MessageModel {


    private List<RemindBean> remind;

    public List<RemindBean> getRemind() {
        return remind;
    }

    public void setRemind(List<RemindBean> remind) {
        this.remind = remind;
    }

    public static class RemindBean {
        /**
         * r_id : 10
         * u_id : 3
         * r_type : 3
         * order_num : 15027742083186171
         * create_time : 1502774223
         * is_read : 0
         * nickname : 靓仔
         * add_time : 1499852540
         * we_price : 0.01
         * pay_type : 支付宝APP支付
         * give_time : 1502774223
         * message : 会员【靓仔】的订单15027742083186171支付金额【0.01】，支付方式【支付宝APP支付】,支付时间【2017-07-12 17:42:20】
         */
        private int is_reply;
        private int r_id;
        private int u_id;
        private int r_type;
        private String order_num;
        private int create_time;
        private int is_read;
        private String nickname;
        private int add_time;
        private String we_price;
        private String pay_type;
        private long give_time;
        private String message;

        public int getIs_reply() {
            return is_reply;
        }

        public void setIs_reply(int is_reply) {
            this.is_reply = is_reply;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public int getR_type() {
            return r_type;
        }

        public void setR_type(int r_type) {
            this.r_type = r_type;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getWe_price() {
            return we_price;
        }

        public void setWe_price(String we_price) {
            this.we_price = we_price;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public long getGive_time() {
            return give_time;
        }

        public void setGive_time(long give_time) {
            this.give_time = give_time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
