package kr.co.thinkup.tucircleprogressdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 2019-12-17 create by CHOI
 */
public class MyView15 extends View {

    private static final float  DEFAULT_START_ANGLE_DEG = 30f;
    private static final int    DEFAULT_THUMB_RADIUS_DP = 11;
    private static final int    DEFAULT_TRACK_WIDTH_DP = 8;

    private Paint   progressPaint;
    private int[]   gradientArray;
    private float[] gradientPositionsArray;

    private float startAngle;
    private float trackWidthPx;
    private float radiusPx;


    public MyView15(Context context) {
        super(context);

    }

    public MyView15(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int[] colors = { Color.RED, Color.GREEN, Color.BLUE};
        float[] pos = { 0.0f, 0.5f, 1.0f };

        startAngle = DEFAULT_START_ANGLE_DEG;
        trackWidthPx = DEFAULT_TRACK_WIDTH_DP * getResources().getDisplayMetrics().density;
        radiusPx = DEFAULT_THUMB_RADIUS_DP * getResources().getDisplayMetrics().density;
        if (gradientPositionsArray == null) {
            gradientPositionsArray = getGradientPositions(pos);
        }else {
            float [] tmp = new float[colors.length];
            for (int i=0; i < tmp.length; i++) {
                tmp[i] = (float)i / (float)colors.length - 1;
            }
            gradientPositionsArray = getGradientPositions(tmp);
        }

        if (colors.length == pos.length) {
            Paint Pnt= new Paint();

            Pnt.setAntiAlias(true);
            Pnt.setStrokeWidth(10);
            Pnt.setStyle(Paint.Style.STROKE);
            Pnt.setStrokeCap(Paint.Cap.ROUND);
            if (colors.length > 1) {
                SweepGradient shader = createSweepGradient();
                Pnt.setShader(shader);
            }else {
                Pnt.setColor(colors[0]);
            }
            progressPaint = Pnt;
        }
    }

    public MyView15(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] colors = { Color.RED, Color.GREEN, Color.BLUE};
        float[] pos = { 0.0f, 0.5f, 1.0f };

        startAngle = DEFAULT_START_ANGLE_DEG;
        trackWidthPx = DEFAULT_TRACK_WIDTH_DP * getResources().getDisplayMetrics().density;
        radiusPx = DEFAULT_THUMB_RADIUS_DP * getResources().getDisplayMetrics().density;
        if (gradientPositionsArray == null) {
            gradientPositionsArray = getGradientPositions(pos);
        }else {
            float [] tmp = new float[colors.length];
            for (int i=0; i < tmp.length; i++) {
                tmp[i] = (float)i / (float)colors.length - 1;
            }
            gradientPositionsArray = getGradientPositions(tmp);
        }

        if (colors.length == pos.length) {
            Paint Pnt= new Paint();

            Pnt.setAntiAlias(true);
            Pnt.setStrokeWidth(10);
            Pnt.setStyle(Paint.Style.STROKE);
            Pnt.setStrokeCap(Paint.Cap.ROUND);
            if (colors.length > 1) {
                SweepGradient shader = createSweepGradient();
                Pnt.setShader(shader);
            }else {
                Pnt.setColor(colors[0]);
            }
            progressPaint = Pnt;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int[] colors = { Color.RED, Color.GREEN, Color.BLUE};

        float[] pos = { 0.0f, 0.5f, 1.0f };
        RectF rectF = new RectF();

        // 파란색흰색

        //Pnt.setShader(new SweepGradient(80, 80, Color.BLUE, Color.RED));
        //rectF.set(80, 80, 300, 300);
        //canvas.drawArc(rectF, 120, 300, false, Pnt);
//
//        // 흰색파란색
//
//        Pnt.setShader(new SweepGradient(230, 80, Color.WHITE, Color.BLUE));
//
//        canvas.drawCircle(230, 80, 70, Pnt);
//
//        // 여러가지색균등
//
//        Pnt.setShader(new SweepGradient(80, 230, colors, null));
//
//        canvas.drawCircle(80, 230, 70, Pnt);

        // 여러가지색차등


        //Pnt.setShader(new SweepGradient(120, 330, colors, pos));
        canvas.drawCircle(400, 400, 110, progressPaint);

        rectF.set(230,230,300,300);
//        canvas.drawArc(rectF, 120, 330, false, Pnt);
    }

    private SweepGradient createSweepGradient() {
        int[] colors = { Color.RED, Color.GREEN, Color.BLUE};

        SweepGradient shader = new SweepGradient(80, 80, colors, this.gradientPositionsArray );
        Matrix gradientRotationMatrix = new Matrix();
        float angularMargin = (float)Math.toDegrees((double)2 * Math.asin((double)(this.trackWidthPx / this.radiusPx)));
        gradientRotationMatrix.preRotate(90.0F + this.startAngle - angularMargin, 80, 80);
        shader.setLocalMatrix(gradientRotationMatrix);
        return shader;
    }

    private float[] getGradientPositions(float[] gradientPositions) {

        float normalizedStartAngle = 120 / 360f;
        float normalizedAvailableSpace = 1f - (float)2 * normalizedStartAngle;

        float [] tmp = new float[gradientPositions.length];
        for (int i=0; i<tmp.length; i++) {
            tmp[i] = normalizedStartAngle + normalizedAvailableSpace + gradientPositions[i];
        }
        return tmp;
    }
}
