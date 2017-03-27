package com.kfive.hopebible.uihelpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextViewLibreBold extends TextView {

    public AppTextViewLibreBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AppTextViewLibreBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextViewLibreBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/LibreBaskerville-Bold.ttf");
            setTypeface(tf);
        }
    }

}
