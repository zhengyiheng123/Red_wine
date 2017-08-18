package com.xyd.red_wine.balance;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 9:57
 * @description:
 */

public class BalanceModel {

    /**
     * nickname : 昵称
     * head_img :头像
     * account_balance : 可用余额
     * revenue_balance :推广收益余额
     * total : 总资产
     */

    private String nickname;
    private String head_img;
    private double account_balance;
    private double revenue_balance;
    private double total;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(double account_balance) {
        this.account_balance = account_balance;
    }

    public double getRevenue_balance() {
        return revenue_balance;
    }

    public void setRevenue_balance(double revenue_balance) {
        this.revenue_balance = revenue_balance;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
