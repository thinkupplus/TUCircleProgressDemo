package kr.co.thinkup.tucircleprogressdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import kr.co.thinkup.tucircleprogress.Utils;

/**
 * 2019-12-17 create by CHOI
 */
public class TUArcProgress extends View {

    private Paint paint;
    protected Paint textPaint;

    private RectF rectF = new RectF();

    private SweepGradient gradient;

    private int width;
    private int height;
    private int[]   gradientArray;
    private float[] gradientPositionsArray;

    private float strokeWidth;
    private float suffixTextSize;
    private float bottomTextSize;
    private String bottomText;
    private float textSize;
    private int textColor;
    private int progress = 0;
    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float arcAngle;
    private String suffixText = "%";
    private float suffixTextPadding;

    private float arcBottomHeight;

    private int default_finished_color = Color.WHITE;
    private int default_unfinished_color = Color.rgb(72, 106, 176);
    private int default_text_color = Color.rgb(66, 145, 241);
    private float default_suffix_text_size;
    private float default_suffix_padding;
    private float default_bottom_text_size;
    private float default_stroke_width;
    private String default_suffix_text;
    private int default_max = 100;
    private float default_arc_angle = 360 * 0.8f;
    private float default_text_size;
    private int min_size;

    public TUArcProgress(Context context) {
        super(context);
    }

    public TUArcProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TUArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_text_size = Utils.sp2px(getResources(), 18);
        min_size = (int) Utils.dp2px(getResources(), 100);
        default_text_size = Utils.sp2px(getResources(), 40);
        default_suffix_text_size = Utils.sp2px(getResources(), 15);
        default_suffix_padding = Utils.dp2px(getResources(), 4);
        default_suffix_text = "%";
        default_bottom_text_size = Utils.sp2px(getResources(), 10);
        default_stroke_width = Utils.dp2px(getResources(), 4);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, kr.co.thinkup.tucircleprogress.R.styleable.ArcProgress, defStyleAttr, 0);

        attributes.recycle();
        initPainters();
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public float getSuffixTextSize() {
        return suffixTextSize;
    }

    public void setSuffixTextSize(float suffixTextSize) {
        this.suffixTextSize = suffixTextSize;
        this.invalidate();
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
        this.invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getBottomTextSize() {
        return bottomTextSize;
    }

    public void setBottomTextSize(float bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
        this.invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    public String getSuffixText() {
        return suffixText;
    }

    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
        this.invalidate();
    }

    public float getSuffixTextPadding() {
        return suffixTextPadding;
    }

    public void setSuffixTextPadding(float suffixTextPadding) {
        this.suffixTextPadding = suffixTextPadding;
        this.invalidate();
    }

    private SweepGradient createSweepGradient() {
        float radius = this.width / 2f;
        int[] colors = { Color.YELLOW, Color.GREEN, Color.RED};
        float[] pos = { 0.0f, 0.35f, 0.7f };
        float startAngle = 270 - arcAngle / 2f;
        SweepGradient shader = new SweepGradient(rectF.centerX(), rectF.centerY(), colors, pos);
        Matrix gradientRotationMatrix = new Matrix();
        float angularMargin = (float)Math.toDegrees((double)2 * Math.asin((double)(this.strokeWidth / radius)));
        gradientRotationMatrix.preRotate(startAngle - angularMargin, rectF.centerX(), rectF.centerY());
        shader.setLocalMatrix(gradientRotationMatrix);
        return shader;
    }

    private float[] getGradientPositions(float[] gradientPositions) {
        float startAngle = 270 - arcAngle / 2f;
        float normalizedStartAngle = startAngle / 360f;
        float normalizedAvailableSpace = 1f - (float)2 * normalizedStartAngle;

        float [] tmp = new float[gradientPositions.length];
        for (int i=0; i<tmp.length; i++) {
            tmp[i] = normalizedStartAngle + normalizedAvailableSpace + gradientPositions[i];
        }
        return tmp;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.width = width;
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        rectF.set(strokeWidth / 2f, strokeWidth / 2f, width - strokeWidth / 2f, MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f);
        float radius = width / 2f;
        float angle = (360 - arcAngle) / 2f;
        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));

        float[] pos = { 0.0f, 0.5f, 1.0f };

        gradientPositionsArray = getGradientPositions(pos);
        gradient = createSweepGradient();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = 270 - arcAngle / 2f;
        float finishedSweepAngle = progress / (float) getMax() * arcAngle;
        float finishedStartAngle = startAngle;
        if(progress == 0) finishedStartAngle = 0.01f;
        paint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint);
//        paint.setColor(finishedStrokeColor);

        paint.setShader(gradient);
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), 200, paint);

        String text = String.valueOf(getProgress());
        if (!TextUtils.isEmpty(text)) {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            float textHeight = textPaint.descent() + textPaint.ascent();
            float textBaseline = (getHeight() - textHeight) / 2.0f;
            canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, textBaseline, textPaint);
            textPaint.setTextSize(suffixTextSize);
            float suffixHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(suffixText, getWidth() / 2.0f  + textPaint.measureText(text) + suffixTextPadding, textBaseline + textHeight - suffixHeight, textPaint);
        }

        if(arcBottomHeight == 0) {
            float radius = getWidth() / 2f;
            float angle = (360 - arcAngle) / 2f;
            arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
        }

        if (!TextUtils.isEmpty(getBottomText())) {
            textPaint.setTextSize(bottomTextSize);
            float bottomTextBaseline = getHeight() - arcBottomHeight - (textPaint.descent() + textPaint.ascent()) / 2;
            canvas.drawText(getBottomText(), (getWidth() - textPaint.measureText(getBottomText())) / 2.0f, bottomTextBaseline, textPaint);
        }
    }
}
