package com.xyd.red_wine.suggest.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.SuggestApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.newsdetail.DetailActivity;
import com.xyd.red_wine.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 9:59
 * @description: 资讯——视频
 */

public class VideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    @Bind(R.id.video_rv)
    RecyclerView videoRv;
    @Bind(R.id.video_srl)
    SwipeRefreshLayout videoSrl;
    private List<VideoModel.ArticlesBean> list;
    private VideoAdapter adapter;
    private int page = 1, num = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        videoSrl.setOnRefreshListener(this);
        videoSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        videoRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        initAdapter();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(SuggestApi.class)
                .video_msg(page, num)
                .compose(RxSchedulers.<BaseModel<VideoModel>>compose())
                .subscribe(new BaseObserver<VideoModel>() {
                    @Override
                    protected void onHandleSuccess(VideoModel videoModel, String msg, int code) {
                        videoSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        if (page == 1) {
                            adapter.setNewData(videoModel.getArticles());
                        } else if (videoModel.getArticles().size() > 0) {
                            adapter.addData(videoModel.getArticles());
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        videoSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        showToast(msg);
                    }
                });
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new VideoAdapter(list, getActivity());
        adapter.setOnLoadMoreListener(this, videoRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        videoRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void onRefresh() {
        page = 1;
        getData();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b=new Bundle();
        b.putInt(VideoActivity.VIDEO_ID,adapter.getData().get(position).getA_id());
        b.putString(VideoActivity.VIDEO_URL,adapter.getData().get(position).getA_content());
        b.putInt(VideoActivity.COLLECT, adapter.getData().get(position).getCollect());
        startActivity(VideoActivity.class,b);
    }
}
