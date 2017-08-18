package com.xyd.red_wine.commitorder;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/2
 * @time: 14:03
 * @description:
 */

public class WxModel {
    private String appid;
    private String partnerid;
    private String prepayid;

    public String getS_package() {
        return s_package;
    }

    public void setS_package(String s_package) {
        this.s_package = s_package;
    }

    private String s_package;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }


    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String noncestr;
    private String timestamp;
    private String sign;


}
