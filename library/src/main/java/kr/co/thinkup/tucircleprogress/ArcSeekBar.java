package kr.co.thinkup.tucircleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 2019-12-16 create by CHOI
 */
public class ArcSeekBar extends View {

    public ArcSeekBar(Context context) {
        this(context, null);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcSeekBar, defStyleAttr, 0);
        attributes.recycle();
    }

}
