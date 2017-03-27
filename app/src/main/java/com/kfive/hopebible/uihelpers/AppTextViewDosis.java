package com.kfive.hopebible.uihelpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextViewDosis extends TextView {

    public AppTextViewDosis(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AppTextViewDosis(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextViewDosis(Context context) {
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
