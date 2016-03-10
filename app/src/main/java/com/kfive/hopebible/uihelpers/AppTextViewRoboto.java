package com.kfive.hopebible.uihelpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextViewRoboto extends TextView {

    public AppTextViewRoboto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AppTextViewRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextViewRoboto(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Dosis-Medium.ttf");
            setTypeface(tf);
        }
    }

}
