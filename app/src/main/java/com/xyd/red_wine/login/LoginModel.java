package com.xyd.red_wine.login;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 14:53
 * @description:
 */

public class LoginModel {

    /**
     * userid : 用户ID
     * phone : 手机号
     * nickname : 昵称
     * signature :个性签名
     * sex : 性别1男0女
     * head_img : 头像
     * pid : 上级ID
     * pid_status : 等级
     * qr_code : 二维码
     * wechat_id : 微信号
     * alipay_id :  支付宝号
     * is_push : 是否接收推送1是0否
     * referral_code : 推荐码
     * account_balance : 账户余额
     * total_revenue : 总收益
     * revenue_balance : 收益余额
     * chest : 公益金
     * noncestr : 随机字符串
     * create_time : 注册时间
     */

    private int userid;
    private String phone;
    private String nickname;
    private String signature;
    private String sex;
    private String head_img;
    private int pid;
    private int pid_status;
    private String qr_code;
    private String wechat_id;
    private String alipay_id;
    private int is_push;
    private String referral_code;
    private String account_balance;
    private String total_revenue;
    private String revenue_balance;
    private String chest;
    private String noncestr;
    private String create_time;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid_status() {
        return pid_status;
    }

    public void setPid_status(int pid_status) {
        this.pid_status = pid_status;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
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

    public int getIs_push() {
        return is_push;
    }

    public void setIs_push(int is_push) {
        this.is_push = is_push;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(String total_revenue) {
        this.total_revenue = total_revenue;
    }

    public String getRevenue_balance() {
        return revenue_balance;
    }

    public void setRevenue_balance(String revenue_balance) {
        this.revenue_balance = revenue_balance;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
