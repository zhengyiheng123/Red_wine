package com.xyd.red_wine.winedetail;

import android.content.DialogInterface;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.StoreApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.commitorder.CommitOrderActivity;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.utils.ToastUtils;
import com.xyd.red_wine.view.SmartImageveiw;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 15:42
 * @description: 小酒
 */

public class WineDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {
    public static String G_ID = "g_id";
    public static String G_DATA = "g_data";
    public static String G_NUM = "g_num";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.wine_cost)
    TextView wineCost;
    @Bind(R.id.wine_buy)
    TextView wineBuy;
    @Bind(R.id.wine_iv)
    SmartImageveiw wineIv;
    @Bind(R.id.wine_name)
    TextView wineName;
    @Bind(R.id.wine_price)
    TextView winePrice;
    @Bind(R.id.wine_name_bottom)
    TextView wineNameBottom;
    @Bind(R.id.wine_state)
    TextView wineState;
    @Bind(R.id.wine_type)
    TextView wineType;
    @Bind(R.id.wine_detail)
    RadioButton wineDetail;
    @Bind(R.id.wine_evaluate)
    RadioButton wineEvaluate;
    @Bind(R.id.id_stickynavlayout_indicator)
    RadioGroup wineRg;
    @Bind(R.id.id_stickynavlayout_viewpager)
    ViewPager wineVp;
    @Bind(R.id.wine_jian)
    ImageView wineJian;
    @Bind(R.id.wine_edt_num)
    EditText wineEdtNum;
    @Bind(R.id.wine_add)
    ImageView wineAdd;
    private Fragment[] fragments;
    private int index = 0;
    private WineDetailFragment wineDetailFragment;
    private WineEvaluateFragment wineEvaluateFragment;
    private String g_id;
    private double price;
    private int num = 1;
    private WineModel model;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_small_wine;
    }

    @Override
    protected void initView() {
        wineIv.setRatio(2.0f);
        g_id = getIntent().getStringExtra(G_ID);
        Log.e("aaaaaa", g_id);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("小酒");
        wineDetailFragment = new WineDetailFragment();
        wineEvaluateFragment = new WineEvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(G_ID, g_id);
        wineEvaluateFragment.setArguments(bundle);
        fragments = new Fragment[]{wineDetailFragment, wineEvaluateFragment};
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        wineVp.setAdapter(adapter);
        wineVp.setCurrentItem(index);
        wineRg.check(R.id.wine_detail);
        wineEdtNum.setText(num + "");
        getData();


    }


    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        wineBuy.setOnClickListener(this);
        wineJian.setOnClickListener(this);
        wineAdd.setOnClickListener(this);
        wineRg.setOnCheckedChangeListener(this);
        wineVp.addOnPageChangeListener(this);

    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(StoreApi.class)
                .goods(g_id)
                .compose(RxSchedulers.<BaseModel<WineModel>>compose())
                .subscribe(new BaseObserver<WineModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    protected void onHandleSuccess(WineModel wineModel, String msg, int code) {
                        model = wineModel;
                        GlideUtil.getInstance()
                                .loadCornerImage(WineDetailActivity.this, wineIv, PublicStaticData.baseUrl + wineModel.getGood().getG_img(), 5);
                        wineName.setText(wineModel.getGood().getG_name());
                        price = wineModel.getGood().getG_price();
                        winePrice.setText("￥" + price);
                        double freignht=Double.valueOf(wineModel.getGood().getG_freight());
                        double goodPrice=price*num;
                        double totalPrice=goodPrice+freignht;
//                        DecimalFormat df=new DecimalFormat("#.##");

                        wineCost.setText("￥" + totalPrice);
                        wineType.setText("规格："+wineModel.getGood().getG_kind());
                        if (wineModel.getGood().getG_freight().equals("0.00"))
                            wineState.setText("免运费");
                        else
                            wineState.setText("运费：￥" + wineModel.getGood().getG_freight());

                        wineNameBottom.setText(wineModel.getGood().getG_sname());
                        wineDetailFragment.setWebView(wineModel.getG_con());

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.wine_buy:
                if (model.getGood().getG_num()>0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(G_DATA, model);
                    bundle.putInt(G_NUM, num);
                    startActivity(CommitOrderActivity.class, bundle);
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("您购买的商品暂时没货，请您购买其它商品");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }

                break;
            case R.id.wine_add:
                if (num < 99) {
                    num++;
                    if (num>model.getGood().getG_num()){
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setTitle("温馨提示");
                        builder.setMessage("当前商品库存"+model.getGood().getG_num()+"件");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                        return;
                    }
                    wineEdtNum.setText(num + "");
                    wineCost.setText("￥" + (price * num + Double.valueOf(model.getGood().getG_freight())));
                }

                break;
            case R.id.wine_jian:
                if (num > 1) {
                    num--;
                    wineEdtNum.setText(num + "");
                    wineCost.setText("￥" + (price * num + Double.valueOf(model.getGood().getG_freight())));
                }
                break;
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                wineRg.check(R.id.wine_detail);
                break;
            case 1:
                wineRg.check(R.id.wine_evaluate);
                break;

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.wine_detail:
                index = 0;
                break;
            case R.id.wine_evaluate:
                index = 1;
                break;
        }
        wineVp.setCurrentItem(index, true);
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
