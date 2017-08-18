package com.xyd.red_wine.evaluate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.StoreApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideCircleTransform;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.order.OrderModel;
import com.xyd.red_wine.permissions.PermissionUtils;
import com.xyd.red_wine.permissions.PermissionsManager;
import com.xyd.red_wine.personinformation.InfromationActivity;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.GetImagePath;
import com.xyd.red_wine.view.CustomDialog;
import com.xyd.red_wine.winedetail.WineDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 11:34
 * @description: 评论界面
 */

public class EvaluateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static final String EVALUATE = "evaluate";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.evaluate_iv)
    ImageView evaluateIv;
    @Bind(R.id.evaluate_nums)
    TextView evaluateNums;
    @Bind(R.id.evaluate_tv_title)
    TextView evaluateTvTitle;
    @Bind(R.id.evaluate_tv_title1)
    TextView evaluateTvTitle1;
    @Bind(R.id.evaluate_item_tv_price)
    TextView evaluateItemTvPrice;
    @Bind(R.id.evaluate_good)
    RadioButton evaluateGood;
    @Bind(R.id.evaluate_middle)
    RadioButton evaluateMiddle;
    @Bind(R.id.evaluate_bad)
    RadioButton evaluateBad;
    @Bind(R.id.evaluate_rg)
    RadioGroup evaluateRg;
    @Bind(R.id.evaluate_edt)
    EditText evaluateEdt;
    @Bind(R.id.evaluate_add_image)
    TextView evaluateAddImage;
    @Bind(R.id.evaluate_send)
    TextView evaluateSend;
    @Bind(R.id.evaluate_rv_image)
    RecyclerView evaluateRvImage;
    private List<File> images;
    private EvaluateAdapter adapter;
    private int type = 3;
    private String g_id,order_num;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initView() {
        OrderModel.MyOrderBean bean= (OrderModel.MyOrderBean) getIntent().getSerializableExtra(EVALUATE);
        g_id=bean.getG_id()+"";
        order_num=bean.getOrder_num();
        GlideUtil.getInstance()
                .loadImage(this,evaluateIv, PublicStaticData.baseUrl+bean.getG_img(),true);
        evaluateTvTitle.setText(bean.getG_name());
        evaluateTvTitle1.setText(bean.getG_sname());
        evaluateNums.setText("X"+bean.getNum());
        evaluateItemTvPrice.setText("￥"+bean.getPrice());
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("评价");
        images = new ArrayList<>();
        evaluateRvImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new EvaluateAdapter(this, images, deleteClick);
        evaluateRvImage.setAdapter(adapter);

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        evaluateRg.setOnCheckedChangeListener(this);
        evaluateAddImage.setOnClickListener(this);
        evaluateSend.setOnClickListener(this);


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.evaluate_send:
                sendEvaluate();


                break;
            case R.id.evaluate_add_image:
                if (images.size() == 2) {
                    showToast("最多选择两张图片");
                } else {
                    showPictureDialog();
                }
                break;
        }

    }

    /**
     * Pinglun
     */
    private void sendEvaluate() {
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("g_id", g_id);

        builder.addFormDataPart("star", type + "");
        builder.addFormDataPart("order_num", order_num + "");
        builder.addFormDataPart("content", evaluateEdt.getText().toString());
        if (images.size() == 1)
            builder.addFormDataPart("img_1", images.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), images.get(0)));
        else if (images.size() > 1) {
            builder.addFormDataPart("img_1", images.get(0).getName(), RequestBody.create(MediaType.parse("image/*"), images.get(0)));
            builder.addFormDataPart("img_2", images.get(1).getName(), RequestBody.create(MediaType.parse("image/*"), images.get(1)));
        }
        BaseApi.getRetrofit()
                .create(StoreApi.class)
                .addComment(builder.build())
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        showToast(msg);
                        for (File flie :images){
                            if (flie.exists())
                                flie.delete();
                        }
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });

    }

    EvaluateAdapter.OnDeleteClick deleteClick = new EvaluateAdapter.OnDeleteClick() {
        @Override
        public void delete(int position) {
            images.remove(position);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.evaluate_good:
                type = 3;
                break;
            case R.id.evaluate_middle:
                type = 2;
                break;
            case R.id.evaluate_bad:
                type = 1;
                break;
        }
    }


    private File file, outputFile;

    private void showPictureDialog() {
        final CustomDialog dialog = new CustomDialog(this, R.layout.dialog_choose_picture, true);
        dialog.findViewById(R.id.dialog_tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionUtils.storage(EvaluateActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        file = FileUtils.createImageFile(System.currentTimeMillis() + ".jpg");
                        chooseFromAlbum();
                    }
                });

            }


        });
        dialog.findViewById(R.id.dialog_tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionUtils.camera(EvaluateActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        file = FileUtils.createImageFile(System.currentTimeMillis() + ".jpg");
                        chooseFromCamera();
                    }
                });

            }
        });
        dialog.findViewById(R.id.dialog_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(EvaluateActivity.this, "com.xyd.red_wine", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 1007);
        } else {
            startActivityForResult(intent, 1008);
        }
    }

    private void chooseFromCamera() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            Uri uriForFile = FileProvider.getUriForFile(this, "com.xyd.red_wine", file);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(intentFromCapture, 1006);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param inputUri
     */
    public void startPhotoZoom(Uri inputUri) {

        if (inputUri == null) {
            Log.i("", "The uri is not exist.");
            return;
        }
        outputFile = FileUtils.createImageFile(System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = Uri.fromFile(outputFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            Uri outPutUri = Uri.fromFile(outputFile);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 18);
        intent.putExtra("aspectY", 11);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 856);
        intent.putExtra("outputY", 390);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult(intent, 1009);//这里就将裁剪后的图片的Uri返回了
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //7.0以下相册
        if (requestCode == 1008 & RESULT_OK == resultCode) {
            startPhotoZoom(data.getData());
        } else if (requestCode == 1008 & RESULT_CANCELED == resultCode) {
            deleteFile();
        }
        //7.0以上相册
        if (requestCode == 1007 & RESULT_OK == resultCode) {
            File imgUri = new File(GetImagePath.getPath(this, data.getData()));
            Uri dataUri = FileProvider.getUriForFile(this, "com.xyd.red_wine", imgUri);
            startPhotoZoom(dataUri);
        } else if (requestCode == 1007 & RESULT_CANCELED == resultCode) {
            deleteFile();
        }
        //拍照
        if (requestCode == 1006 & RESULT_OK == resultCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri inputUri = FileProvider.getUriForFile(this, "com.xyd.red_wine", file);//通过FileProvider创建一个content类型的Uri
                startPhotoZoom(inputUri);//设置输入类型
            } else {
                Uri inputUri = Uri.fromFile(file);
                startPhotoZoom(inputUri);
            }
        } else if (requestCode == 1006 & RESULT_CANCELED == resultCode) {
            deleteFile();
        }
        //裁剪
        if (requestCode == 1009 & RESULT_OK == resultCode) {
            if (file != null)
                file.delete();
            images.add(outputFile);
            adapter.notifyDataSetChanged();
            // dataCivHead.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
        } else if (requestCode == 1009 & RESULT_CANCELED == resultCode) {
            deleteFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private void deleteFile() {
        if (outputFile != null)
            outputFile.delete();
        if (file != null)
            file.delete();
    }
}
