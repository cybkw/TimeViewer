package com.deep.timeviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TimeLineView extends View {

    private float strokeWidth = 1;

    private float margin = 1;

    private Path mPath;

    private Paint paintTemp;
    private Paint paintTemp2;
    private Paint paintBorder;
    private Paint paintBorder2;
    private Paint paintGray;
    private Paint paintWhite;
    private Paint paintBlue;

    private float WIDTH_BAR;
    private float HEIGHT_BAR;

    // 边框
    private String boColor = "#f5f5f5";
    // 过时
    private String edColor = "#f5f5f5";
    // 背景
    private String bgColor = "#FFFFFF";
    // 设置
    private String tiColor = "#78b5fd";

    private Context context;

    private double widthBar;
    private double widthBarLine;
    private double lineBar = 1;

    /**
     *   时间对应参数 - 时间块序号
     *  8:00 -  8:30 -  0
     *  8:30 -  9:00 -  1
     *  9:00 -  9:30 -  2
     *  9:30 - 10:00 -  3
     * 10:00 - 10:30 -  4
     * 10:30 - 11:00 -  5
     * 11:00 - 11:30 -  6
     * 11:30 - 12:00 -  7
     * 12:00 - 12:30 -  8
     * 12:30 - 13:00 -  9
     * 13:00 - 13:30 - 10
     * 13:30 - 14:00 - 11
     * 14:00 - 14:30 - 12
     * 14:30 - 15:00 - 13
     * 15:00 - 15:30 - 14
     * 15:30 - 16:00 - 15
     * 16:00 - 16:30 - 16
     * 16:30 - 17:00 - 17
     * 17:00 - 17:30 - 18
     * 17:30 - 18:00 - 19
     * 18:00 - 18:30 - 20
     * 18:30 - 19:00 - 21
     * 19:00 - 19:30 - 22
     * 19:30 - 20:00 - 23
     * 20:00 - 20:30 - 24
     * 20:30 - 21:00 - 25
     * 21:00 - 21:30 - 26
     * 21:30 - 22:00 - 27
     * 22:00 - 22:30 - 28
     * 22:30 - 23:00 - 29
     */

    /**
     * 每个时间块的结构
     */
    public class JeBean {
        public int state = 0; // 0: 未设置 1: 不可设置 2: 已设置
    }

    /**
     * 三十个时间块
     */
    private List<JeBean> jeBeans;

    public TimeLineView(Context context) {
        super(context);
        init(context);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        if (jeBeans == null) {
            jeBeans = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                jeBeans.add(new JeBean());
            }
        }

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setDither(true);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeCap(Paint.Cap.ROUND);
        paintBorder.setColor(Color.parseColor(boColor));
        paintBorder.setStrokeWidth(strokeWidth);

        paintBorder2 = new Paint();
        paintBorder2.setAntiAlias(true);
        paintBorder2.setDither(true);
        paintBorder2.setStyle(Paint.Style.FILL);
        paintBorder2.setStrokeCap(Paint.Cap.ROUND);
        paintBorder2.setColor(Color.parseColor(boColor));
        paintBorder2.setStrokeWidth(strokeWidth);

        paintGray = new Paint();
        paintGray.setAntiAlias(true);
        paintGray.setDither(true);
        paintGray.setStyle(Paint.Style.FILL);
        paintGray.setStrokeCap(Paint.Cap.ROUND);
        paintGray.setColor(Color.parseColor(edColor));

        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setDither(true);
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setStrokeCap(Paint.Cap.ROUND);
        paintWhite.setColor(Color.parseColor(bgColor));

        paintBlue = new Paint();
        paintBlue.setAntiAlias(true);
        paintBlue.setDither(true);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setStrokeCap(Paint.Cap.ROUND);
        paintBlue.setColor(Color.parseColor(tiColor));

        mPath = new Path();

        widthBarLine = (double) (WIDTH_BAR - lineBar * (jeBeans.size() / 2 - 1)) / (double) (jeBeans.size() / 2);
        widthBar = (double) (WIDTH_BAR) / (double) (jeBeans.size());
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    private float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dpValue * scale + 0.5f);
    }

    /**
     * 设置边框颜色
     *
     * @param boColor
     */
    public void setBoColor(String boColor) {
        this.boColor = boColor;
        paintBorder.setColor(Color.parseColor(boColor));
        paintBorder2.setColor(Color.parseColor(boColor));
        invalidate();
    }

    /**
     * 设置过时颜色
     *
     * @param edColor
     */
    public void setEdColor(String edColor) {
        this.edColor = edColor;
        paintGray.setColor(Color.parseColor(edColor));
        invalidate();
    }

    /**
     * 设置背景颜色 未设置
     *
     * @param bgColor
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
        paintWhite.setColor(Color.parseColor(bgColor));
        invalidate();
    }

    /**
     * 设置已设置颜色
     *
     * @param tiColor
     */
    public void setTiColor(String tiColor) {
        this.tiColor = tiColor;
        paintBlue.setColor(Color.parseColor(tiColor));
        invalidate();
    }

    public void setZhiState(int number, int state) {
        JeBean jeBean = new JeBean();
        jeBean.state = state;
        jeBeans.set(number, jeBean);
        invalidate();
    }

    /**
     * 线条粗细
     *
     * @param strokeWidth
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.margin = strokeWidth;
        this.lineBar = strokeWidth;
        widthBarLine = (double) (WIDTH_BAR - lineBar * (jeBeans.size() / 2 - 1)) / (double) (jeBeans.size() / 2);
        widthBar = (double) (WIDTH_BAR) / (double) (jeBeans.size());
        invalidate();
    }

    // 8 - 23 15时 30格
    // 1 灰 2 白 3 蓝色

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < jeBeans.size() / 2; i++) {
            float x = (float) (widthBarLine * i + lineBar * i);
            float w = (float) ((float) (widthBarLine * (i + 1) + lineBar * (i + 1)) - (float) (widthBarLine * i + lineBar * i)) / 2;

            int k = i * 2;
            if (jeBeans.get(k).state == 0) {
                paintTemp = paintWhite;
            } else if (jeBeans.get(k).state == 1) {
                paintTemp = paintGray;
            } else {
                paintTemp = paintBlue;
            }
            if (jeBeans.get(k + 1).state == 0) {
                paintTemp2 = paintWhite;
            } else if (jeBeans.get(k + 1).state == 1) {
                paintTemp2 = paintGray;
            } else {
                paintTemp2 = paintBlue;
            }
            if (i == 0) {
                drawRoundRect(canvas, paintTemp, margin, margin, w, HEIGHT_BAR, HEIGHT_BAR / 2,
                        true, true, false, false);

                drawRoundRect(canvas, paintTemp2, margin + w - 1, margin, w, HEIGHT_BAR, HEIGHT_BAR / 2,
                        false, false, false, false);
            } else if (jeBeans.size() / 2 - 1 == i) {
                drawRoundRect(canvas, paintTemp, margin + x, margin, w, HEIGHT_BAR, HEIGHT_BAR / 2,
                        false, false, false, false);
                drawRoundRect(canvas, paintTemp2, margin + x + w - 1, margin, w - strokeWidth, HEIGHT_BAR, HEIGHT_BAR / 2,
                        false, false, true, true);
            } else {
                drawRoundRect(canvas, paintTemp, margin + x, margin, w, HEIGHT_BAR, HEIGHT_BAR / 2,
                        false, false, false, false);
                drawRoundRect(canvas, paintTemp2, margin + x + w - 1, margin, w, HEIGHT_BAR, HEIGHT_BAR / 2,
                        false, false, false, false);
            }
            if (i > 0) {
                canvas.drawRect(margin + x - 1, margin, (float) (margin + x - 1 + lineBar), HEIGHT_BAR + margin, paintBorder2);
            }
        }
        drawRoundRect(canvas, paintBorder, margin, margin, WIDTH_BAR, HEIGHT_BAR, (HEIGHT_BAR) / 2,
                true, true, true, true);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float x, float y, float w, float h, float r,
                               boolean leftTop, boolean leftBottom, boolean rightTop, boolean rightBottom) {
        mPath.reset();
        if (leftTop) {
            mPath.moveTo(x + r, y);
        } else {
            mPath.moveTo(x, y);
        }
        if (rightTop) {
            mPath.lineTo(x + w - r, y);
            mPath.arcTo(x + w - 2 * r, y, x + w, y + 2 * r, 270, 90, false);
        } else {
            mPath.lineTo(x + w, y);
        }
        if (rightBottom) {
            mPath.lineTo(x + w, y + h - r);
            mPath.arcTo(x + w - 2 * r, y + h - 2 * r, x + w, y + h, 0, 90, false);
        } else {
            mPath.lineTo(x + w, y + h);
        }
        if (leftBottom) {
            mPath.lineTo(x + r, y + h);
            mPath.arcTo(x, y + h - 2 * r, x + 2 * r, y + h, 90, 90, false);
        } else {
            mPath.lineTo(x, y + h);
        }
        if (leftTop) {
            mPath.arcTo(x, y, x + 2 * r, y + 2 * r, 180, 90, false);
        }
        mPath.close();
        canvas.drawPath(mPath, paint);
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
                WIDTH_BAR = WIDTH_BAR - margin * 2;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                WIDTH_BAR = defaultWidth - margin * 2;
                break;
        }
        return (int) WIDTH_BAR + 2;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                HEIGHT_BAR = getMobileWidth(context) - margin * 2;
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                HEIGHT_BAR = defaultHeight - margin * 2;
                break;
        }
        return (int) HEIGHT_BAR + 2;
    }
}
