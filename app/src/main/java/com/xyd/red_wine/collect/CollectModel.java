package com.xyd.red_wine.collect;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 10:17
 * @description:
 */

public class CollectModel implements Serializable {
    private List<MyCollectBean> my_collect;

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    private int model;

    public List<MyCollectBean> getMy_collect() {
        return my_collect;
    }

    public void setMy_collect(List<MyCollectBean> my_collect) {
        this.my_collect = my_collect;
    }

    public static class MyCollectBean {
        /**
         * a_title : qq
         * a_img : ["/uploads/20170711/65d044f6e7ebad1576192ab9bc269c67.jpg"]
         * create_time : 1970-01-01 08:00:00
         * collect :是否收藏1已收藏0未收藏   collect
         * t_id://类型4：图文。5:视频
         */

        private String a_title;
        private String create_time;
        private List<String> a_img;
        private int a_id;
        private int t_id;

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public int getA_id() {
            return a_id;
        }

        public void setA_id(int a_id) {
            this.a_id = a_id;
        }

        public String getA_content() {
            return a_content;
        }

        public void setA_content(String a_content) {
            this.a_content = a_content;
        }

        private String a_content;


        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        private int collect;
        public String getA_title() {
            return a_title;
        }

        public void setA_title(String a_title) {
            this.a_title = a_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<String> getA_img() {
            return a_img;
        }

        public void setA_img(List<String> a_img) {
            this.a_img = a_img;
        }
    }
}
