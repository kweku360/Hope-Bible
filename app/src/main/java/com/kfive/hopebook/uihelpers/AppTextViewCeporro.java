package com.kfive.hopebook.uihelpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextViewCeporro extends TextView {

    public AppTextViewCeporro(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AppTextViewCeporro(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextViewCeporro(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Ceporro.ttf");
            setTypeface(tf);
        }
    }

}
