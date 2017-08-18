package com.xyd.red_wine.api;

import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.primeur.PrimeurModel;
import com.xyd.red_wine.suggest.chateau.ChateauAddressModel;
import com.xyd.red_wine.suggest.chateau.ChateauModel;
import com.xyd.red_wine.suggest.image.ImageModel;
import com.xyd.red_wine.suggest.video.VideoModel;
import com.xyd.red_wine.winesteward.NewsModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/21
 * @time: 13:29
 * @description:
 */

public interface SuggestApi {
    /**
     * 酒庄地区
     *
     * @return
     */
    @POST("message/chateau_add")
    Observable<BaseModel<ChateauAddressModel>> chateau_add();


    /**
     * 红酒管家资讯
     *
     * @return
     */
    @POST("message/steward_msg")
    Observable<BaseModel<NewsModel>> steward_msg(@Query("page") int page,
                                                 @Query("num") int num);

    /**
     * 期酒资讯
     *
     * @return
     */
    @POST("message/wins_msg")
    Observable<BaseModel<PrimeurModel>> wins_msg(@Query("page") int page,
                                                 @Query("num") int num);

    /**
     * 酒庄资讯
     *
     * @return
     */
    @POST("message/chateau_msg")
    Observable<BaseModel<ChateauModel>> chateau_msg(@Query("page") int page,
                                                    @Query("num") int num,
                                                    @Query("t_id") int t_id,
                                                    @Query("a_title")String a_title);

    /**
     * 图文资讯
     *
     * @return
     */
    @POST("message/photo_msg")
    Observable<BaseModel<ImageModel>> photo_msg(@Query("page") int page,
                                                @Query("num") int num);

    /**
     * 视频资讯
     *
     * @return
     */
    @POST("message/video_msg")
    Observable<BaseModel<VideoModel>> video_msg(@Query("page") int page,
                                                @Query("num") int num);


}
