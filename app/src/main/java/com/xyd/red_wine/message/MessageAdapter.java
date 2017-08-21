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
        helper.setText(R.id.item_message_time,"来自：系统管理员"+ TimeUtils.stampToDateSdemand( item.getCreate_time()+"","yyyy.MM.dd HH:mm"));
        if (item.getR_type() == 4){
            helper.setVisible(R.id.tv_reply,true);
            if (item.getIs_reply() == 0){
                helper.setText(R.id.tv_reply,"未回复");
            }else {
                helper.setText(R.id.tv_reply,"");
            }
            helper.setText(R.id.item_message_time,"来自："+item.getNickname()+" "+ TimeUtils.stampToDateSdemand( item.getCreate_time()+"","yyyy.MM.dd HH:mm"));
        }else {
            helper.setText(R.id.item_message_time,"来自：系统管理员"+ " "+TimeUtils.stampToDateSdemand( item.getCreate_time()+"","yyyy.MM.dd HH:mm"));
        }

    }
}
