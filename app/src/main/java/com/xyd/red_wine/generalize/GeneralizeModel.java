package com.xyd.red_wine.generalize;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/26
 * @time: 17:48
 * @description:
 */

public class GeneralizeModel {

    /**
     * qr_code : /uploads/code/1500449927288245.png
     * articles : {"photo_msg":[{"a_id":2,"a_img":["/uploads/20170711/65d044f6e7ebad1576192ab9bc269c67.jpg"],"a_title":"qq"}],"video_msg":[{"a_id":5,"a_img":["/uploads/20170719/3ce247bf91833927d16524f481943b28.jpg"],"a_title":"一个骚男"}]}
     */

    private String qr_code;
    private ArticlesBean articles;
    private String share_url;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public ArticlesBean getArticles() {
        return articles;
    }

    public void setArticles(ArticlesBean articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        private List<PhotoMsgBean> photo_msg;
        private List<VideoMsgBean> video_msg;

        public List<PhotoMsgBean> getPhoto_msg() {
            return photo_msg;
        }

        public void setPhoto_msg(List<PhotoMsgBean> photo_msg) {
            this.photo_msg = photo_msg;
        }

        public List<VideoMsgBean> getVideo_msg() {
            return video_msg;
        }

        public void setVideo_msg(List<VideoMsgBean> video_msg) {
            this.video_msg = video_msg;
        }

        public static class PhotoMsgBean {
            /**
             * a_id : 2
             * a_img : ["/uploads/20170711/65d044f6e7ebad1576192ab9bc269c67.jpg"]
             * a_title : qq
             */
            private int a_id;
            private String a_title;
            private List<String> a_img;

            public int getCollect() {
                return collect;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            private int collect;


            public String getA_content() {
                return a_content;
            }

            public void setA_content(String a_content) {
                this.a_content = a_content;
            }

            private String a_content;

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

            public List<String> getA_img() {
                return a_img;
            }

            public void setA_img(List<String> a_img) {
                this.a_img = a_img;
            }
        }

        public static class VideoMsgBean {
            /**
             * a_id : 5
             * a_img : ["/uploads/20170719/3ce247bf91833927d16524f481943b28.jpg"]
             * a_title : 一个骚男
             */

            private int a_id;
            private String a_title;
            private List<String> a_img;
            private int collect;

            public int getCollect() {
                return collect;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            public String getA_content() {
                return a_content;
            }

            public void setA_content(String a_content) {
                this.a_content = a_content;
            }

            private String a_content;
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

            public List<String> getA_img() {
                return a_img;
            }

            public void setA_img(List<String> a_img) {
                this.a_img = a_img;
            }
        }
    }
}
