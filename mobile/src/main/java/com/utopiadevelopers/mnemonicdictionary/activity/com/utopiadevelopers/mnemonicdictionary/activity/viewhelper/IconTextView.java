package com.utopiadevelopers.mnemonicdictionary.activity.com.utopiadevelopers.mnemonicdictionary.activity.viewhelper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class IconTextView extends TextView
{
    public IconTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs,defStyle);
    }

    public IconTextView(Context context)
    {
        super(context);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "custom.ttf");
        setTypeface(myTypeface);
    }
}
