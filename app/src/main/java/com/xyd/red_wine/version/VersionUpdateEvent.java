package com.xyd.red_wine.version;

/**
 * @author: zhaoxiaolei
 * @date: 2017/5/9
 * @time: 9:08
 * @description:
 */

public class VersionUpdateEvent {
    private boolean showTips;

    public boolean isShowTips() {
        return showTips;
    }

    public void setShowTips(boolean showTips) {
        this.showTips = showTips;
    }
}
