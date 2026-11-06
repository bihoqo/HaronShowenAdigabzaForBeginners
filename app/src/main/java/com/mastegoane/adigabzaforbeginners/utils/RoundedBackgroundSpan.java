package com.mastegoane.adigabzaforbeginners.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mastegoane.adigabzaforbeginners.R;

public class RoundedBackgroundSpan extends ReplacementSpan {
    public RoundedBackgroundSpan(Context context) {
        super();
        mContext = context;
        mBackgroundFillColor = mContext.getResources().getColor(R.color.question_type9_bg_fill);
        mBackgroundStrokeColor = mContext.getResources().getColor(R.color.question_type9_bg_stroke);
        mTextColor = mContext.getResources().getColor(R.color.question_type9_text);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end) + 0.5f);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        final DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        final float mDp2pxFactor = displayMetrics.density;
        final float strokeThikness = mDp2pxFactor * 2;
        final float paddingVertical1 = mDp2pxFactor * 3;
        final float paddingVertical2 = mDp2pxFactor * 0;
        final float paddingHorizontal = mDp2pxFactor * 6;

//        final RectF fillRect = new RectF(x + strokeThikness, top + strokeThikness, x + measureText(paint, text, start, end) - strokeThikness, bottom - strokeThikness);
        final RectF fillRect = new RectF(x - paddingHorizontal, top - paddingVertical1, x + measureText(paint, text, start, end) + paddingHorizontal, bottom + paddingVertical2);
//        final RectF strokeRect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(mBackgroundFillColor);
        canvas.drawRoundRect(fillRect, CORNER_RADIUS, CORNER_RADIUS, paint);

        final Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeThikness);
        strokePaint.setColor(mBackgroundStrokeColor);
//        canvas.drawRoundRect(strokeRect, CORNER_RADIUS, CORNER_RADIUS, strokePaint);
        canvas.drawRoundRect(fillRect, CORNER_RADIUS, CORNER_RADIUS, strokePaint);

        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    private float measureText(@NonNull Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

    private static int CORNER_RADIUS = 8;
    private int mBackgroundFillColor = 0;
    private int mBackgroundStrokeColor = 0;
    private int mTextColor = 0;

    private final Context mContext;
}
