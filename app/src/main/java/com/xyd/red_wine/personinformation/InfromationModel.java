package com.xyd.red_wine.personinformation;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 9:53
 * @description:
 */

public class InfromationModel {


    /**
     * userid : 用户id
     * phone : 手机号
     * nickname : 昵称
     * sex : 性别1男0女
     * head_img : 头像
     * wechat_id :微信号
     * alipay_id :支付宝账号
     * signature :个性签名
     */

    private int userid;
    private String phone;
    private String nickname;
    private String sex;
    private String head_img;
    private String wechat_id;
    private String alipay_id;
    private String signature;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getAlipay_id() {
        return alipay_id;
    }

    public void setAlipay_id(String alipay_id) {
        this.alipay_id = alipay_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
