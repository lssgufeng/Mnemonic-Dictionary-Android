package com.utopiadevelopers.mnemonicdictionary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utopiadevelopers.mnemonicdictionary.R;
import com.utopiadevelopers.mnemonicdictionary.helpers.CommonLib;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class SplashActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1500);
                    Intent i =null;
                    if(CommonLib.isUserLoggedIn(SplashActivity.this))
                    {
                        i = new Intent(SplashActivity.this, Dashboard.class);
                    }
                    else
                    {
                        i = new Intent(SplashActivity.this,LoginActivity.class);
                    }
                    startActivity(i);
                }
                catch (Exception e)
                {

                }
            }
        });

        t.start();
    }

}
