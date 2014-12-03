package com.utopiadevelopers.mnemonicdictionary.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.utopiadevelopers.mnemonicdictionary.R;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class SplashActivity extends Activity
{
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView test = (TextView) findViewById(R.id.logoIcon);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "custom.ttf");
        test.setTypeface(myTypeface);
    }
}
