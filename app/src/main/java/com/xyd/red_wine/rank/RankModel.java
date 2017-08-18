package com.xyd.red_wine.rank;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 11:59
 * @description:
 */

public class RankModel {
    /**
     * revenue:收益排行榜
     * chest:公益排行榜
     */
    private List<RevenueBean> revenue;
    private List<ChestBean> chest;

    public List<RevenueBean> getRevenue() {
        return revenue;
    }

    public void setRevenue(List<RevenueBean> revenue) {
        this.revenue = revenue;
    }

    public List<ChestBean> getChest() {
        return chest;
    }

    public void setChest(List<ChestBean> chest) {
        this.chest = chest;
    }

    public static class RevenueBean {
        /**
         * head_img :
         * nickname : rkGIax
         * total_revenue : 5.00
         */

        private String head_img;
        private String nickname;
        private String total_revenue;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        private int rank;

        public int getLev() {
            return lev;
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        private  int lev;

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

        public String getTotal_revenue() {
            return total_revenue;
        }

        public void setTotal_revenue(String total_revenue) {
            this.total_revenue = total_revenue;
        }
    }

    public static class ChestBean {
        /**
         * head_img :
         * nickname : x9TxAM
         * chest : 5.00
         */

        private String head_img;
        private String nickname;
        private String chest;
        public int getLev() {
            return lev;
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        private  int lev;
        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        private int rank;
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

        public String getChest() {
            return chest;
        }

        public void setChest(String chest) {
            this.chest = chest;
        }
    }
}
