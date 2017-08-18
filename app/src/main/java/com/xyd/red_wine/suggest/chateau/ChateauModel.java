package com.xyd.red_wine.suggest.chateau;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 13:51
 * @description:
 */

public class ChateauModel {


    private List<ArticlesBean> articles;

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * a_id : 6
         * a_title : 测试
         * a_content : <p>aa</p>
         * a_img : ["/uploads/20170720/fcbea1d167b1333228918147ebfd2e3f.jpg","/uploads/20170720/9e508f75118f70c0e0933066c11b4b6a.jpg","/uploads/20170720/f9c551f80befa22e11f2fe244eaddab9.jpg"]
         * create_time : 2017-07-20 19:14:27
         */

        private int a_id;
        private String a_title;

        public String getA_stitle() {
            return a_stitle;
        }

        public void setA_stitle(String a_stitle) {
            this.a_stitle = a_stitle;
        }

        private String a_stitle;
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
