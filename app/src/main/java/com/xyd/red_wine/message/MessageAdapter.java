package com.xyd.red_wine.message;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.utils.TimeUtils;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:59
 * @description:
 */

public class MessageAdapter extends BaseQuickAdapter<MessageModel.RemindBean,BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageModel.RemindBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel.RemindBean item) {
        helper.setText(R.id.item_message_content,item.getMessage());
        helper.setText(R.id.item_message_time,"来自：系统管理员"+ TimeUtils.millis2String( item.getCreate_time()*1000,"yyyy-MM-dd HH:mm"));
    }
}
