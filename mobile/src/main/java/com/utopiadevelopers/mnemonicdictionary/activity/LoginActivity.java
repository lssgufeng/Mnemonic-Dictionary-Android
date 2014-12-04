package com.utopiadevelopers.mnemonicdictionary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.utopiadevelopers.mnemonicdictionary.R;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class LoginActivity extends Activity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_sign_facebook:
                break;

            case R.id.button_sign_google:
                break;

            case R.id.button_sign_email:
                break;

            case R.id.button_login:
                break;

            default:
                break;
        }
    }
}
