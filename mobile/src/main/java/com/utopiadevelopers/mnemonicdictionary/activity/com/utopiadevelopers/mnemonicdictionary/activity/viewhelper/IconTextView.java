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

    public IconTextView(Context context)
    {
        super(context);
        setCustomFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setCustomFont(context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        setCustomFont(context);
    }

    private void setCustomFont(Context context)
    {
        if(context!=null&&!isInEditMode())
        {
            Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "custom.ttf");
            setTypeface(myTypeface);
        }
    }
}
