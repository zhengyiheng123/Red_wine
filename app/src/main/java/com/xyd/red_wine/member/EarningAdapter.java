package com.xyd.red_wine.member;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:27
 * @description:
 */

public class EarningAdapter extends BaseQuickAdapter<EarningModel.DeductBean, BaseViewHolder> {
    private Context context;
    private final String[] strings;

    public EarningAdapter(@Nullable List<EarningModel.DeductBean> data, Context context) {
        super(R.layout.item_earnings, data);
        this.context = context;
        strings = new String[]{"上级","一级","二级","三级","四级","五级","六级","七级","八级","九级","十级",};
    }

    @Override
    protected void convert(BaseViewHolder helper, EarningModel.DeductBean item) {
        if (item!=null) {
            GlideUtil.getInstance()
                    .loadCircleImage(context, (ImageView) helper.getView(R.id.earnings_iv), PublicStaticData.baseUrl + item.getHead_img());

        helper.setText(R.id.earnings_tv_name, item.getNickname());

        helper.setText(R.id.earnings_tv_level, strings[item.getPid_status()]);
        helper.setText(R.id.earnings_tv_sign, item.getSignature());
        }
    }
}
