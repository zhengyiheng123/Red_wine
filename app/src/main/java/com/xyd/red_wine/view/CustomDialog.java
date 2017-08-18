package com.xyd.red_wine.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.xyd.red_wine.R;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int layout) {
        this(context, layout, R.style.CustomDialogTheme);
    }
    public CustomDialog(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);
       // setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f; //背景透明
        window.setAttributes(params);
    }


    public CustomDialog(Context context, int layout, Boolean isBottom) {
        super(context, R.style.CustomDialogTheme);
        setContentView(layout);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        //setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f; //背景透明
        window.setAttributes(params);
    }


}
