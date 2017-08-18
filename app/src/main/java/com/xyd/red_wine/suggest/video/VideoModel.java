package com.xyd.red_wine.suggest.video;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 10:41
 * @description:
 */

public class VideoModel {

    private List<ArticlesBean> articles;

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * a_id : 2
         * a_title : qq
         * a_content : <p>aa</p>
         * a_img : ["/uploads/20170711/65d044f6e7ebad1576192ab9bc269c67.jpg"]
         * create_time : 1970-01-01 08:00:00
         */

        private int a_id;
        private String a_title;
        private String a_content;
        private String create_time;
        private List<String> a_img;

        public int getA_id() {
            return a_id;
        }

        public void setA_id(int a_id) {
            this.a_id = a_id;
        }

        public String getA_title() {
            return a_title;
        }

        public void setA_title(String a_title) {
            this.a_title = a_title;
        }

        public String getA_content() {
            return a_content;
        }

        public void setA_content(String a_content) {
            this.a_content = a_content;
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
