package com.xyd.red_wine.suggest.chateau;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/21
 * @time: 13:31
 * @description:
 */

public class ChateauAddressModel {


    private List<ChateatAddBean> chateat_add;

    public List<ChateatAddBean> getChateat_add() {
        return chateat_add;
    }

    public void setChateat_add(List<ChateatAddBean> chateat_add) {
        this.chateat_add = chateat_add;
    }

    public static class ChateatAddBean {
        /**
         * t_id : 8
         * title : 西班牙
         */

        private int t_id;
        private String title;

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
