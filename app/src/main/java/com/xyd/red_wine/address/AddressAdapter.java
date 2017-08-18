package com.xyd.red_wine.address;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/25
 * @time: 15:08
 * @description:
 */

public class AddressAdapter extends BaseQuickAdapter<AddressModel.MyAddressBean, BaseViewHolder> {
    private Context context;

    public AddressAdapter(@Nullable List<AddressModel.MyAddressBean> data, Context context) {
        super(R.layout.item_address, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressModel.MyAddressBean item) {
        helper.setText(R.id.address_name, item.getA_name());
        helper.setText(R.id.address_phone, item.getLink_phone());
        helper.setText(R.id.address_content, item.getA_address());
        if (item.getIs_default() == 2)
            helper.setChecked(R.id.address_moren, false);
        else
            helper.setChecked(R.id.address_moren, true);

        helper.addOnClickListener(R.id.address_moren_ll);
        helper.addOnClickListener(R.id.address_del);
        helper.addOnClickListener(R.id.address_edit);
    }
}
