package com.xyd.red_wine.personinformation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xyd.red_wine.R;
import com.xyd.red_wine.address.AddressActivity;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideCircleTransform;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.member.EarningActivity;
import com.xyd.red_wine.member.InputDialog;
import com.xyd.red_wine.permissions.PermissionUtils;
import com.xyd.red_wine.permissions.PermissionsManager;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.GetImagePath;
import com.xyd.red_wine.utils.ToastUtils;
import com.xyd.red_wine.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/10
 * @time: 14:35
 * @description: 个人信息
 */

public class InfromationActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.information_edt_nickname)
    EditText informationEdtNickname;
    @Bind(R.id.information_edt_signature)
    EditText informationEdtSignature;
    @Bind(R.id.information_cb_women)
    CheckBox informationCbWomen;
    @Bind(R.id.information_cb_men)
    CheckBox informationCbMen;
    @Bind(R.id.information_edt_phone)
    TextView informationEdtPhone;
    @Bind(R.id.information_edt_tuijianren)
    TextView information_edt_tuijianren;
    @Bind(R.id.information_edt_wx)
    EditText informationEdtWx;
    @Bind(R.id.information_edt_alipay)
    EditText informationEdtAlipay;
    @Bind(R.id.information_tv_address)
    TextView informationTvAddress;
    @Bind(R.id.information_iv_head)
    ImageView informationIvHead;
    @Bind(R.id.information_iv_add)
    ImageView informationIvAdd;
    @Bind(R.id.information_tv_id)
    TextView informationTvId;
    @Bind(R.id.information_btn_save)
    Button informationBtnSave;
    @Bind(R.id.tv_bangding)
    TextView tv_bangding;
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;
    private File outputFile;
    private File file;
    private InputDialog.Builder dialogBuilder;
    //手机号
    private String phoneNum;
    private InfromationModel model;
    private PromptDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initView() {
        dialog = new PromptDialog(InfromationActivity.this);
        mHeadLine.setVisibility(View.GONE);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("个人信息");
        EventBus.getDefault().register(this);
    }

    /**
     * 获取个人信息
     */
    private void getData() {
        dialog.showLoading("加载中");
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .information()
                .compose(RxSchedulers.<BaseModel<InfromationModel>>compose())
                .subscribe(new BaseObserver<InfromationModel>() {
                    @Override
                    protected void onHandleSuccess(InfromationModel infromationModel, String msg, int code) {
                        dialog.dismissImmediately();
                        model = infromationModel;
                        if (TextUtils.isEmpty(infromationModel.getS_nickname())){
                            information_edt_tuijianren.setText("无推荐人");
                        }else {
                            information_edt_tuijianren.setText(infromationModel.getS_nickname());
                        }
                        if (infromationModel.getIs_buyed() == 1){
                            informationTvId.setText(infromationModel.getUserid());
                        }else {
                            informationTvId.setText("******");
                        }

                        PublicStaticData.sharedPreferences.edit().putString("signature",infromationModel.getSignature()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("nickname",infromationModel.getNickname()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("head",infromationModel.getHead_img()).commit();
                        GlideUtil.getInstance().loadCircleImage(InfromationActivity.this, informationIvHead, PublicStaticData.baseUrl + infromationModel.getHead_img());
                        informationEdtNickname.setText(infromationModel.getNickname());
                        informationEdtPhone.setText(infromationModel.getPhone());
                        informationEdtWx.setText(infromationModel.getWechat_id());
                        informationEdtAlipay.setText(infromationModel.getAlipay_id());
                        informationEdtSignature.setText(infromationModel.getSignature());
                        if (infromationModel.getSex().equals("0"))
                            informationCbWomen.setChecked(true);
                        else
                            informationCbMen.setChecked(true);
                        if (TextUtils.isEmpty(infromationModel.getPhone())){
                            tv_bangding.setText("绑定");
                            tv_bangding.setTextColor(getResources().getColor(R.color.material_white));
                            tv_bangding.setBackgroundResource(R.drawable.bg_btn_quit);
                        }else {
                            tv_bangding.setText("");
                            tv_bangding.setBackgroundResource(R.drawable.pen);
                        }
                        EventBus.getDefault().post(new InformationMessage());
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dialog.dismissImmediately();
                        showTestToast(msg);

                    }
                });


    }

    @Override
    protected void initEvent() {

        baseTitleBack.setOnClickListener(this);
        baseTitleMenu.setOnClickListener(this);
        informationIvAdd.setOnClickListener(this);
        informationTvAddress.setOnClickListener(this);
        informationBtnSave.setOnClickListener(this);
        informationCbWomen.setOnCheckedChangeListener(this);
        informationCbMen.setOnCheckedChangeListener(this);
        tv_bangding.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.base_title_menu:
                showTestToast("menu");
                break;
            case R.id.information_iv_add:
                file = FileUtils.createImageFile(System.currentTimeMillis() + ".jpg");
                showPictureDialog();
                break;
            case R.id.information_tv_address:
               startActivity(AddressActivity.class);
                break;
            case R.id.information_btn_save:
                phoneNum=informationEdtPhone.getText().toString();
                commitData();
                break;
            case R.id.tv_bangding:
                if (TextUtils.isEmpty(model.getPhone())){
                    startActivity(new Intent(getApplicationContext(),BindActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(),ChangePhoneActivity.class));
                }
                break;
        }

    }

    /**
     * 提交修改的信息
     */
    private void commitDataPhone() {
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("phone", phoneNum);

        BaseApi.getRetrofit()
                .create(MineApi.class)
                .user_edit(builder.build())
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        showToast(msg);
                        getData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showTestToast(msg);
                    }
                });


    }
    /**
     * 提交修改的信息
     */
    private void commitData() {
        dialog.showLoading("修改中");
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("nickname", informationEdtNickname.getText().toString());
        builder.addFormDataPart("phone", phoneNum);

        builder.addFormDataPart("signature", informationEdtSignature.getText().toString());
        builder.addFormDataPart("wechat_id", informationEdtWx.getText().toString());
        builder.addFormDataPart("alipay_id", informationEdtAlipay.getText().toString());
        if (informationCbWomen.isChecked())
            builder.addFormDataPart("sex", "0");
        else
            builder.addFormDataPart("sex", "1");

        if (outputFile != null)
            builder.addFormDataPart("head_img", outputFile.getName(), RequestBody.create(MediaType.parse("image/*"), outputFile));
        else
            builder.addFormDataPart("head_img", "");

        BaseApi.getRetrofit()
                .create(MineApi.class)
                .user_edit(builder.build())
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        dialog.dismissImmediately();
                        showToast(msg);
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dialog.dismissImmediately();
                        showTestToast(msg);
                    }
                });


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.information_cb_women:
                if (isChecked)
                    informationCbMen.setChecked(false);
                else
                    informationCbMen.setChecked(true);
                break;
            case R.id.information_cb_men:
                if (isChecked)
                    informationCbWomen.setChecked(false);
                else
                    informationCbWomen.setChecked(true);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void showPictureDialog() {
        final CustomDialog dialog = new CustomDialog(this, R.layout.dialog_choose_picture, true);
        dialog.findViewById(R.id.dialog_tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionUtils.storage(InfromationActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        chooseFromAlbum();
                    }
                });

            }


        });
        dialog.findViewById(R.id.dialog_tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PermissionUtils.camera(InfromationActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
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
            Uri uriForFile = FileProvider.getUriForFile(InfromationActivity.this, "com.xyd.red_wine", file);
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
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
        } else if (requestCode == 1007 & RESULT_CANCELED == resultCode){
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
        } else if (requestCode == 1006 & RESULT_CANCELED == resultCode){
            deleteFile();
        }
        //裁剪
        if (requestCode == 1009 & RESULT_OK == resultCode) {
            BitmapFactory.decodeFile(outputFile.getAbsolutePath());
            Glide.with(this).load(outputFile).error(R.mipmap.head)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //缓存策略
                    .bitmapTransform(new GlideCircleTransform(this)).into(informationIvHead);
            // dataCivHead.setImageBitmap(BitmapFactory.decodeFile(outputFile.getAbsolutePath()));
        } else if (requestCode == 1009 & RESULT_CANCELED == resultCode){
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



     @Subscribe
      public void onEventBus(InformationMessage m){

     }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
