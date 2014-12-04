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
public class SplashActivity extends Activity implements View.OnClickListener
{
    public Button signupButton;
    public Button loginButton;

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

        signupButton = (Button) findViewById(R.id.button_signup);
        loginButton  = (Button) findViewById(R.id.button_login);

        if(CommonLib.isUserLoggedIn(this))
        {

        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LinearLayout app_logo = (LinearLayout) findViewById(R.id.linearLayout);
            if(Build.VERSION.SDK_INT >= 12)
            {
                app_logo.animate().translationYBy(-150);
            }
            else
            {

            }

            signupButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);

            signupButton.setOnClickListener(this);
            loginButton.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_login:
                Intent i1 = new Intent(this,LoginActivity.class);
                startActivity(i1);
                break;
            case R.id.button_signup:
                Intent i2 = new Intent(this,LoginActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }
}
