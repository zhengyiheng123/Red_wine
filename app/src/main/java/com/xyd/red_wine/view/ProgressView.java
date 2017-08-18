package com.xyd.red_wine.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/21
 * @time: 14:00
 * @description:
 */

public class ProgressView extends View {

    private Paint paint;
    private float height;
    private float width;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    private float progress = 0.0f;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        height = getHeight();
        width = getWidth();
        float radius = height / 2;
        paint.setColor(Color.argb(255, 226, 135, 6));
        canvas.drawCircle(radius, radius, radius, paint);

        initPaint();
        paint.setARGB(255, 232, 232, 232);
        canvas.drawCircle(width - radius, radius, radius, paint);
        canvas.drawRect((width - height) * progress + radius, 0, width - radius, height, paint);
        if (progress == 1.0f) {
            initPaint();
            paint.setColor(Color.argb(255, 226, 135, 6));
            canvas.drawCircle(width - radius, radius, radius, paint);
        }

        initPaint();
        paint.setColor(Color.argb(255, 226, 135, 6));
        canvas.drawRect(radius, 0, (width - height) * progress + radius, height, paint);
        initPaint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(25);
        Paint.FontMetrics fm = paint.getFontMetrics();
        int m= (int) (progress*100);

        float fFontWidth = paint.measureText(m + "%");
        float fFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
        canvas.drawText(m + "%", (width - height) * progress / 2-fFontWidth/2, height - fFontHeight / 2, paint);
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
    }

}
