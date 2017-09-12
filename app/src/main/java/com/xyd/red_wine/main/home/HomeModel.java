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
     * banner : {"adv_imgs":"/uploads/20170728/aaeb709634152833ea93ad75a67402ee.jpg","adv_url":"http://www.baidu.com"}
     * carousel : [{"adv_imgs":"/uploads/20170728/8f983245dcf3c007d064a3f465543996.jpg","adv_url":"http://www.baidu.com"},{"adv_imgs":"/uploads/20170726/36cc719c7a623899aac1fcaa15681bf7.jpg","adv_url":"http://www.baidu.com"},{"adv_imgs":"/uploads/20170728/9c42e6458ddfb18d1fdf129095909332.jpg","adv_url":"http://www.baidu.com"}]
     * list : {"wine_introduce":"http://hj.jiangliping.com/index/detail/sundry/s_id/1","recommend_rule":"http://hj.jiangliping.com/index/detail/sundry/s_id/2","manage_rule":"http://hj.jiangliping.com/index/detail/sundry/s_id/3"}
     * photo_msg : [{"a_id":23,"a_title":"意大利顶级名酒\u201c四雅\u201d外传","a_content":"http://hj.jiangliping.com/index/detail/message/a_id/23","collect":0},{"a_id":20,"a_title":"奔富换塞诊所香港站为400瓶稀世珍酿诊断","a_content":"http://hj.jiangliping.com/index/detail/message/a_id/20","collect":0},{"a_id":18,"a_title":"中国前10大葡萄酒进口国，你知道哪些？","a_content":"http://hj.jiangliping.com/index/detail/message/a_id/18","collect":0}]
     * goods : [{"g_id":13,"g_name":"玛歌酒庄红葡萄酒","g_sname":"Chateau Margaux, Margaux, France","g_price":"299.00","g_num":9999,"g_store":0,"g_img":"/uploads/20170801/6f30d59d2b4cbef8454dcd78d36e4168.jpg","g_kind":"750ml","g_freight":"0.00"},{"g_id":14,"g_name":"干露羊驼莫斯卡托甜白葡萄酒","g_sname":"Vicuna Moscato, Itata Valley, Chile","g_price":"399.00","g_num":998,"g_store":1,"g_img":"/uploads/20170801/4f689af0f2445811022202a1570c3210.jpg","g_kind":"750ml","g_freight":"0.00"}]
     */

    private BannerBean banner;
    private ListBean list;
    private List<CarouselBean> carousel;
    private List<PhotoMsgBean> photo_msg;
    private List<GoodsBean> goods;

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

    public List<PhotoMsgBean> getPhoto_msg() {
        return photo_msg;
    }

    public void setPhoto_msg(List<PhotoMsgBean> photo_msg) {
        this.photo_msg = photo_msg;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class BannerBean {
        /**
         * adv_imgs : /uploads/20170728/aaeb709634152833ea93ad75a67402ee.jpg
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
         * wine_introduce : http://hj.jiangliping.com/index/detail/sundry/s_id/1
         * recommend_rule : http://hj.jiangliping.com/index/detail/sundry/s_id/2
         * manage_rule : http://hj.jiangliping.com/index/detail/sundry/s_id/3
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

    public static class CarouselBean {
        /**
         * adv_imgs : /uploads/20170728/8f983245dcf3c007d064a3f465543996.jpg
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

    public static class PhotoMsgBean {
        /**
         * a_id : 23
         * a_title : 意大利顶级名酒“四雅”外传
         * a_content : http://hj.jiangliping.com/index/detail/message/a_id/23
         * collect : 0
         */

        private int a_id;
        private String a_title;
        private String a_content;
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

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }
    }

    public static class GoodsBean {
        /**
         * g_id : 13
         * g_name : 玛歌酒庄红葡萄酒
         * g_sname : Chateau Margaux, Margaux, France
         * g_price : 299.00
         * g_num : 9999
         * g_store : 0
         * g_img : /uploads/20170801/6f30d59d2b4cbef8454dcd78d36e4168.jpg
         * g_kind : 750ml
         * g_freight : 0.00
         */

        private int g_id;
        private String g_name;
        private String g_sname;
        private String g_price;
        private int g_num;
        private int g_store;
        private String g_img;
        private String g_kind;
        private String g_freight;

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getG_sname() {
            return g_sname;
        }

        public void setG_sname(String g_sname) {
            this.g_sname = g_sname;
        }

        public String getG_price() {
            return g_price;
        }

        public void setG_price(String g_price) {
            this.g_price = g_price;
        }

        public int getG_num() {
            return g_num;
        }

        public void setG_num(int g_num) {
            this.g_num = g_num;
        }

        public int getG_store() {
            return g_store;
        }

        public void setG_store(int g_store) {
            this.g_store = g_store;
        }

        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }

        public String getG_kind() {
            return g_kind;
        }

        public void setG_kind(String g_kind) {
            this.g_kind = g_kind;
        }

        public String getG_freight() {
            return g_freight;
        }

        public void setG_freight(String g_freight) {
            this.g_freight = g_freight;
        }
    }
}
