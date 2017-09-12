package com.xyd.red_wine.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.GeneralizeApi;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.collect.CollectActivity;
import com.xyd.red_wine.commissionorder.CommissionOrderActivity;
import com.xyd.red_wine.generalize.GeneralizeActivity;
import com.xyd.red_wine.generalize.GeneralizeModel;
import com.xyd.red_wine.main.mine.MineFragment;
import com.xyd.red_wine.message.MessageActivity;
import com.xyd.red_wine.order.OrderActivity;
import com.xyd.red_wine.rank.RankActivity;
import com.xyd.red_wine.setting.SettingActivity;
import com.xyd.red_wine.utils.ToastUtils;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/26
 * @time: 11:55
 * @description:
 */

public class MenuPopWindow extends PopupWindow implements View.OnClickListener{
    private Context context;
    private View circle;

    public MenuPopWindow(Context context) {
        super(context);
        init(context);
        queryMessage();
    }


    public MenuPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        queryMessage();
    }

    public MenuPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        queryMessage();
    }

    public MenuPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        queryMessage();
    }

    private void init(Context context) {
        this.context=context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_menu, null, false);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(true);
        TextView collection = (TextView) view.findViewById(R.id.pop_tv_collection);
        TextView order = (TextView) view.findViewById(R.id.pop_tv_order);
        TextView order1 = (TextView) view.findViewById(R.id.pop_tv_commission_order);
        TextView rank = (TextView) view.findViewById(R.id.pop_tv_rank);
        TextView code = (TextView) view.findViewById(R.id.pop_tv_code);
        TextView messages = (TextView) view.findViewById(R.id.pop_tv_messages);
        circle = (View) view.findViewById(R.id.pop_view_circle);

        collection.setOnClickListener(this);
        order1.setOnClickListener(this);
        order.setOnClickListener(this);
        rank.setOnClickListener(this);
        code.setOnClickListener(this);
        messages.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_tv_collection:
                startActivity(CollectActivity.class);
                break;
            case R.id.pop_tv_commission_order:
                startActivity(CommissionOrderActivity.class);
                break;
            case R.id.pop_tv_order:
                startActivity(OrderActivity.class);
                break;
            case R.id.pop_tv_rank:
                startActivity(RankActivity.class);
                break;
            case R.id.pop_tv_code:
                getData();

                break;
            case R.id.pop_tv_messages:
                startActivity(MessageActivity.class);
                circle.setVisibility(View.GONE);
                break;
//            case R.id.pop_tv_setting:
//                startActivity(SettingActivity.class);
//                break;
        }



    }
    private void startActivity(Class<?> c){
        Intent i=new Intent(context,c);
        context.startActivity(i);
    }

    /**
     * 购买商品后才能分享
     */
    private void getData() {
        BaseApi.getRetrofit()
                .create(GeneralizeApi.class)
                .generalize()
                .compose(RxSchedulers.<BaseModel<GeneralizeModel>>compose())
                .subscribe(new BaseObserver<GeneralizeModel>() {
                    @Override
                    protected void onHandleSuccess(GeneralizeModel generalizeModel, String msg, int code) {
                        startActivity(GeneralizeActivity.class);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.show("您需购买商品后才能拥有分享二维码！");
                    }
                });
    }
    //查询是否有未读消息
    private void queryMessage(){
        BaseApi.getRetrofit().create(MineApi.class)
                .s_index()
                .compose(RxSchedulers.<BaseModel<MineFragment.Is_Read>>compose())
                .subscribe(new BaseObserver<MineFragment.Is_Read>() {
                    @Override
                    protected void onHandleSuccess(MineFragment.Is_Read is_read, String msg, int code) {
                        if (is_read.getIs_read() == 0){
                            circle.setVisibility(View.VISIBLE);
                        }else {
                            circle.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }

}
