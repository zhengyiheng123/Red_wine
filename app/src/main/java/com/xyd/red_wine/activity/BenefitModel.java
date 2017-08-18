package com.xyd.red_wine.activity;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 11:59
 * @description:
 */

public class BenefitModel {


    private List<ActiveBean> active;

    public List<ActiveBean> getActive() {
        return active;
    }

    public void setActive(List<ActiveBean> active) {
        this.active = active;
    }

    public static class ActiveBean {
        /**
         * ac_id : 4
         * ac_title : 测试
         * ac_slogan : 测试测试
         * ac_type : 1
         * ac_state : 0
         * now_num : 1122
         * support_num : 200
         * have_support : 800
         * ac_img : /uploads/20170712/4ba280f80c258c3a7833b1e1446baba4.jpg
         */

        private int ac_id;
        private String ac_title;
        private String ac_slogan;

        public String getA_content() {
            return a_content;
        }

        public void setA_content(String a_content) {
            this.a_content = a_content;
        }

        private String a_content;
        private int ac_type;
        private int ac_state;
        private double now_num;

        public double getAll_num() {
            return all_num;
        }

        public void setAll_num(double all_num) {
            this.all_num = all_num;
        }

        private double all_num;
        private int support_num;
        private int have_support;
        private String ac_img;

        public int getAc_id() {
            return ac_id;
        }

        public void setAc_id(int ac_id) {
            this.ac_id = ac_id;
        }

        public String getAc_title() {
            return ac_title;
        }

        public void setAc_title(String ac_title) {
            this.ac_title = ac_title;
        }

        public String getAc_slogan() {
            return ac_slogan;
        }

        public void setAc_slogan(String ac_slogan) {
            this.ac_slogan = ac_slogan;
        }

        public int getAc_type() {
            return ac_type;
        }

        public void setAc_type(int ac_type) {
            this.ac_type = ac_type;
        }

        public int getAc_state() {
            return ac_state;
        }

        public void setAc_state(int ac_state) {
            this.ac_state = ac_state;
        }

        public double getNow_num() {
            return now_num;
        }

        public void setNow_num(double now_num) {
            this.now_num = now_num;
        }

        public int getSupport_num() {
            return support_num;
        }

        public void setSupport_num(int support_num) {
            this.support_num = support_num;
        }

        public int getHave_support() {
            return have_support;
        }

        public void setHave_support(int have_support) {
            this.have_support = have_support;
        }

        public String getAc_img() {
            return ac_img;
        }

        public void setAc_img(String ac_img) {
            this.ac_img = ac_img;
        }
    }
}
