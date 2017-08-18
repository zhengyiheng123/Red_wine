package com.xyd.red_wine.member;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 13:29
 * @description:
 */

public class MemberAdapter extends BaseQuickAdapter<MemberModel.ExchangeBean,BaseViewHolder> {
    public MemberAdapter( @Nullable List<MemberModel.ExchangeBean> data) {
        super(R.layout.item_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberModel.ExchangeBean item) {
        if (item.getLev()==1){
            helper.setVisible(R.id.item_member_receiver,false);
            helper.setVisible(R.id.item_member_sender,true);
            helper.setText(R.id.item_member_sender,item.getSender()+":"+item.getE_comment());
        }else {
            helper.setVisible(R.id.item_member_receiver,true);
            helper.setVisible(R.id.item_member_sender,false);
            helper.setText(R.id.item_member_receiver,item.getReceiver()+":"+item.getE_comment());
        }

    }
}
