package com.xyd.red_wine.address;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 11:14
 * @description:
 */

public class AddressModel {


    private List<MyAddressBean> my_address;

    public List<MyAddressBean> getMy_address() {
        return my_address;
    }

    public void setMy_address(List<MyAddressBean> my_address) {
        this.my_address = my_address;
    }

    public static class MyAddressBean {
        /**
         * a_id : 119
         * a_name : 董
         * a_area : 浙江省 杭州市 上城区
         * a_address : 山水人家
         * is_default : 1
         * link_phone : 15342689523
         */

        private int a_id;
        private String a_name;
        private String a_area;
        private String a_address;
        private int is_default;
        private String link_phone;

        public int getA_id() {
            return a_id;
        }

        public void setA_id(int a_id) {
            this.a_id = a_id;
        }

        public String getA_name() {
            return a_name;
        }

        public void setA_name(String a_name) {
            this.a_name = a_name;
        }

        public String getA_area() {
            return a_area;
        }

        public void setA_area(String a_area) {
            this.a_area = a_area;
        }

        public String getA_address() {
            return a_address;
        }

        public void setA_address(String a_address) {
            this.a_address = a_address;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getLink_phone() {
            return link_phone;
        }

        public void setLink_phone(String link_phone) {
            this.link_phone = link_phone;
        }
    }
}
