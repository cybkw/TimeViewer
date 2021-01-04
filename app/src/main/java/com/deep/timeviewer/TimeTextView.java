package com.deep.timeviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

public class TimeTextView extends View {

    private Paint paintFont;

    private float WIDTH_BAR;
    private float HEIGHT_BAR;

    private float tWidth;
    private float fWidth;
    private float fSize = 35;

    // 边框
    private String fontColor = "#000000";

    private Context context;

    public TimeTextView(Context context) {
        super(context);
        init(context);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setDither(true);
        paintFont.setStyle(Paint.Style.FILL);
        paintFont.setStrokeCap(Paint.Cap.ROUND);
        paintFont.setColor(Color.parseColor(fontColor));
        paintFont.setTextSize(fSize);

        tWidth = WIDTH_BAR / (23 - 8 + 2);
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    private float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dpValue * scale + 0.5f);
    }

    /**
     * 设置颜色
     *
     * @param fontColor
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
        paintFont.setColor(Color.parseColor(fontColor));
        invalidate();
    }

    /**
     * 设置颜色
     *
     * @param fontColor f
     */
    public void setFontSize(float fSize) {
        this.fSize = fSize;
        paintFont.setTextSize(fSize);
        invalidate();
    }

    public int getTextWidth(Paint mPaint, String str) {
        Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect);
        int w = rect.width();
        int h = rect.height();
        return w;
    }

    // 8 - 23 15时 30格
    // 1 灰 2 白 3 蓝色

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i <= 23 - 8 + 1; i++) {
            float x = i * tWidth;

            String str = String.valueOf(i + 7);
            //1. 粗略计算文字宽度：
            fWidth = getTextWidth(paintFont, str);

            canvas.drawText(String.valueOf(i + 7), x - fWidth / 2, fSize - fSize / 6, paintFont);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);

        init(context);
    }

    /**
     * get screen width of this cellphone
     *
     * @param context
     * @return
     */
    private int getMobileWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                WIDTH_BAR = getMobileWidth(context);
                WIDTH_BAR = WIDTH_BAR;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                WIDTH_BAR = defaultWidth;
                break;
        }
        return (int) WIDTH_BAR;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                HEIGHT_BAR = getMobileWidth(context);
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                HEIGHT_BAR = defaultHeight;
                break;
        }
        return (int) fSize;
    }
}
