package com.xyd.red_wine.main.home;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/27
 * @time: 13:16
 * @description:
 */

public class HomeModel {

    /**
     * banner : {"adv_imgs":"/uploads/20170726/c49f078d7a371187c2207bc9f71ad0be.jpg","adv_url":"http://www.baidu.com"}
     * carousel : [{"adv_imgs":"/uploads/20170726/bb97e42c7d052f9a758b919c8dbc6c5a.jpg","adv_url":"http://www.baidu.com"},{"adv_imgs":"/uploads/20170726/36cc719c7a623899aac1fcaa15681bf7.jpg","adv_url":"http://www.baidu.com"},{"adv_imgs":"/uploads/20170726/f6163d77493b6024d7facce8b655e194.jpg","adv_url":"http://www.baidu.com"}]
     * list : {"wine_introduce":"http://hj.jiangliping.com/index/detail/wine_introduce","recommend_rule":"http://hj.jiangliping.com/index/detail/recommend_rule","manage_rule":"http://hj.jiangliping.com/index/detail/manage_rule"}
     * photo_msg : {"a_id":11,"a_title":"意大利最好的十八款酒，大家有听过吗？","a_content":"http://hj.jiangliping.com/index/detail/message/a_id/11","a_img":["/uploads/20170726/ee92c2b7c87e668a8686e30529bd3a64.jpg"],"a_con":"顺序从左到右第一组：一级园VOSNE ROMANEE LES CHAUMES1 GROS F&S LES CHAUMES:橡木味比较明显，果味以红醋栗和李子为主。酒体比较柔和，回味略短。酸度，果味，酒"}
     */

    private BannerBean banner;
    private ListBean list;

    public List<PhotoMsgBean> getPhoto_msg() {
        return photo_msg;
    }

    public void setPhoto_msg(List<PhotoMsgBean> photo_msg) {
        this.photo_msg = photo_msg;
    }

    private List<PhotoMsgBean> photo_msg;
    private List<CarouselBean> carousel;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }


    public List<CarouselBean> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<CarouselBean> carousel) {
        this.carousel = carousel;
    }

    public static class BannerBean {
        /**
         * adv_imgs : /uploads/20170726/c49f078d7a371187c2207bc9f71ad0be.jpg
         * adv_url : http://www.baidu.com
         */

        private String adv_imgs;
        private String adv_url;

        public String getAdv_imgs() {
            return adv_imgs;
        }

        public void setAdv_imgs(String adv_imgs) {
            this.adv_imgs = adv_imgs;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }
    }

    public static class ListBean {
        /**
         * wine_introduce : http://hj.jiangliping.com/index/detail/wine_introduce
         * recommend_rule : http://hj.jiangliping.com/index/detail/recommend_rule
         * manage_rule : http://hj.jiangliping.com/index/detail/manage_rule
         */

        private String wine_introduce;
        private String recommend_rule;
        private String manage_rule;

        public String getWine_introduce() {
            return wine_introduce;
        }

        public void setWine_introduce(String wine_introduce) {
            this.wine_introduce = wine_introduce;
        }

        public String getRecommend_rule() {
            return recommend_rule;
        }

        public void setRecommend_rule(String recommend_rule) {
            this.recommend_rule = recommend_rule;
        }

        public String getManage_rule() {
            return manage_rule;
        }

        public void setManage_rule(String manage_rule) {
            this.manage_rule = manage_rule;
        }
    }

    public static class PhotoMsgBean {
        /**
         * a_id : 11
         * a_title : 意大利最好的十八款酒，大家有听过吗？
         * a_content : http://hj.jiangliping.com/index/detail/message/a_id/11
         * a_img : ["/uploads/20170726/ee92c2b7c87e668a8686e30529bd3a64.jpg"]
         * a_con : 顺序从左到右第一组：一级园VOSNE ROMANEE LES CHAUMES1 GROS F&S LES CHAUMES:橡木味比较明显，果味以红醋栗和李子为主。酒体比较柔和，回味略短。酸度，果味，酒
         */

        private int a_id;
        private String a_title;
        private String a_content;



        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        private int collect;

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


    }

    public static class CarouselBean {
        /**
         * adv_imgs : /uploads/20170726/bb97e42c7d052f9a758b919c8dbc6c5a.jpg
         * adv_url : http://www.baidu.com
         */

        private String adv_imgs;
        private String adv_url;

        public String getAdv_imgs() {
            return adv_imgs;
        }

        public void setAdv_imgs(String adv_imgs) {
            this.adv_imgs = adv_imgs;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }
    }
}
