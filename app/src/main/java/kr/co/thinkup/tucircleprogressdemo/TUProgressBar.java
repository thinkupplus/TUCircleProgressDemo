package kr.co.thinkup.tucircleprogressdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 2019-12-17 create by CHOI
 */
public class TUProgressBar extends ProgressBar {

    public TUProgressBar(Context context) {
        super(context);
    }

    public TUProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TUProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void draw(Canvas paramCanvas) {
        int i = getMeasuredWidth();
        int j = getMeasuredHeight();
        paramCanvas.save();
        paramCanvas.rotate(135.0F, i / 2, j / 2);
        super.draw(paramCanvas);
        paramCanvas.restore();
    }

}
