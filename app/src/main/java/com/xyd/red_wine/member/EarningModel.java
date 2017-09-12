package com.xyd.red_wine.member;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:27
 * @description:
 */

public class EarningModel {


    private List<DeductBean> deduct;

    public List<DeductBean> getDeduct() {
        return deduct;
    }

    public void setDeduct(List<DeductBean> deduct) {
        this.deduct = deduct;
    }

    public static class DeductBean {
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        /**
         * userid : 2
         * nickname : cW5I9q
         * head_img :
         * signature :
         * pid_status : 1
         */
private String phone;
        private long create_time;
        private int userid;
        private String nickname;
        private String head_img;
        private String signature;
        private int pid_status;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getPid_status() {
            return pid_status;
        }

        public void setPid_status(int pid_status) {
            this.pid_status = pid_status;
        }
    }
}
