package com.xyd.red_wine.version;

/**
 * @author: zhaoxiaolei
 * @date: 2017/5/8
 * @time: 14:30
 * @description:  版本更新
 */

public class VersionUpdateModel {


    /**
     * v_id : 4
     * version_num : 1.0
     * desc : 测试版IOS
     * upload_url : http://www.baidu.com
     * add_time : 1502692380
     * version : 2
     */

    private int v_id;
    private String version_num;
    private String desc;
    private String upload_url;
    private int add_time;
    private int version;

    public boolean isMustUpgrade() {
        return mustUpgrade;
    }

    public void setMustUpgrade(boolean mustUpgrade) {
        this.mustUpgrade = mustUpgrade;
    }

    private boolean mustUpgrade;
    public boolean isNeedUpgrade() {
        return needUpgrade;
    }

    public void setNeedUpgrade(boolean needUpgrade) {
        this.needUpgrade = needUpgrade;
    }

    private boolean needUpgrade;

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
