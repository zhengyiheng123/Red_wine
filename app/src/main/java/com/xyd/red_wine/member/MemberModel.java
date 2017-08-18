package com.xyd.red_wine.member;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 11:44
 * @description:
 */

public class MemberModel {

    private List<ExchangeBean> exchange;

    public List<ExchangeBean> getExchange() {
        return exchange;
    }

    public void setExchange(List<ExchangeBean> exchange) {
        this.exchange = exchange;
    }

    public static class ExchangeBean {
        /**
         * e_id : 18
         * u_id : 1
         * pid : 3
         * e_comment : 大风刮过
         * create_time : 1502863063
         * lev : 1
         * sender : 孤独音乐人
         * receiver : 靓仔
         */

        private int e_id;
        private int u_id;
        private int pid;
        private String e_comment;
        private int create_time;
        private int lev;
        private String sender;
        private String receiver;

        public int getE_id() {
            return e_id;
        }

        public void setE_id(int e_id) {
            this.e_id = e_id;
        }

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getE_comment() {
            return e_comment;
        }

        public void setE_comment(String e_comment) {
            this.e_comment = e_comment;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getLev() {
            return lev;
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }
    }
}
