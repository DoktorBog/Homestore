package com.myregistry.homestore.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ProximaNovaTextView extends AppCompatTextView{

    public ProximaNovaTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ProximaNovaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ProximaNovaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/ProximaNova_Regular.ttf");
        setTypeface(customFont);
    }
}
