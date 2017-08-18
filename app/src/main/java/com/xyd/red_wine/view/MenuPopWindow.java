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
import com.xyd.red_wine.collect.CollectActivity;
import com.xyd.red_wine.commissionorder.CommissionOrderActivity;
import com.xyd.red_wine.order.OrderActivity;
import com.xyd.red_wine.rank.RankActivity;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/26
 * @time: 11:55
 * @description:
 */

public class MenuPopWindow extends PopupWindow implements View.OnClickListener{
    private Context context;
    public MenuPopWindow(Context context) {
        super(context);
        init(context);
    }


    public MenuPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MenuPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
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
        TextView order1 = (TextView) view.findViewById(R.id.pop_tv_order1);
        TextView rank = (TextView) view.findViewById(R.id.pop_tv_rank);
        collection.setOnClickListener(this);
        order1.setOnClickListener(this);
        order.setOnClickListener(this);
        rank.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_tv_collection:
                startActivity(CollectActivity.class);
                break;
            case R.id.pop_tv_order1:
                startActivity(CommissionOrderActivity.class);
                break;
            case R.id.pop_tv_order:
                startActivity(OrderActivity.class);
                break;
            case R.id.pop_tv_rank:
                startActivity(RankActivity.class);
                break;
        }



    }
    private void startActivity(Class<?> c){
        Intent i=new Intent(context,c);
        context.startActivity(i);
    }
}
