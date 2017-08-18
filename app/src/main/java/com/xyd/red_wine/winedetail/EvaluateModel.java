package com.xyd.red_wine.winedetail;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 16:08
 * @description:
 */

public class EvaluateModel {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * head_img : /uploads/20170714/2985fae94acf4703bfe7fed821a33f56.jpg
         * nickname : 孤独音乐人
         * content : 空荡荡
         * pubtime : 1500874027
         * img_1 : /uploads/20170717/0dd747cffa83974685941ebbed1314e7.jpg
         * img_2 :
         * star : 3
         * g_name : 测试商品
         * g_kind :
         * num : 2
         */

        private String head_img;
        private String nickname;
        private String content;
        private String pubtime;
        private String img_1;
        private String img_2;
        private int star;
        private String g_name;
        private String g_kind;
        private int num;

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPubtime() {
            return pubtime;
        }

        public void setPubtime(String pubtime) {
            this.pubtime = pubtime;
        }

        public String getImg_1() {
            return img_1;
        }

        public void setImg_1(String img_1) {
            this.img_1 = img_1;
        }

        public String getImg_2() {
            return img_2;
        }

        public void setImg_2(String img_2) {
            this.img_2 = img_2;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getG_kind() {
            return g_kind;
        }

        public void setG_kind(String g_kind) {
            this.g_kind = g_kind;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
