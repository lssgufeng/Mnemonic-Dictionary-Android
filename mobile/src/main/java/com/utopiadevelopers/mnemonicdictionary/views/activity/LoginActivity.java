package com.utopiadevelopers.mnemonicdictionary.views.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.utopiadevelopers.mnemonicdictionary.R;
import com.utopiadevelopers.mnemonicdictionary.UtopiaApplication;
import com.utopiadevelopers.mnemonicdictionary.helpers.ConnectionDetector;
import com.utopiadevelopers.mnemonicdictionary.helpers.MyConfig;
import com.utopiadevelopers.mnemonicdictionary.json.LoginResponseObject;
import com.utopiadevelopers.mnemonicdictionary.networking.GsonGetRequest;
import com.utopiadevelopers.mnemonicdictionary.views.fragment.AppLogoFragment;
import com.utopiadevelopers.mnemonicdictionary.views.fragment.LoginFragment;
import com.utopiadevelopers.mnemonicdictionary.views.fragment.SignUpEmailFragment;
import com.utopiadevelopers.mnemonicdictionary.views.fragment.SignUpFragment;
import com.utopiadevelopers.mnemonicdictionary.helpers.CommonLib;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class LoginActivity extends FragmentActivity implements SignUpFragment.SignUpListener,LoginFragment.LoginListener
{
    private UtopiaApplication application;
    private SharedPreferences prefrences;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fragment topFragment = new AppLogoFragment();
        Fragment botFragment = new SignUpFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragment_login_top, topFragment);
        transaction.add(R.id.fragment_login_bottom, botFragment);

        transaction.commit();

        application = (UtopiaApplication) getApplication();
        prefrences = getSharedPreferences(MyConfig.SHAREDPREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void onButtonClick(View v)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (v.getId())
        {
            case R.id.button_sign_facebook:

                break;

            case R.id.button_sign_google:

                break;

            case R.id.button_sign_email:
                Fragment newSignUpFragment = new SignUpEmailFragment();

                transaction.replace(R.id.fragment_login_bottom, newSignUpFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                break;

            case R.id.button_login:
                Fragment newLoginFragment = new LoginFragment();

                transaction.replace(R.id.fragment_login_bottom, newLoginFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                break;

            default:
                break;
        }
    }

    @Override
    public void normalLoginWith(String email, String password)
    {
        ConnectionDetector detector = new ConnectionDetector(this);
        if(detector.isConnectingToInternet())
        {
            String finalUrl = MyConfig.API_LOGIN +  "email=" + email + "&password=" + CommonLib.getMd5Hash(password);

            CommonLib.ulog("Request",finalUrl);

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Authenticating user...");
            pDialog.show();

            RequestQueue requestQueue = application.getRequestQueue();

            GsonGetRequest<LoginResponseObject> loginRequest = new GsonGetRequest<LoginResponseObject>(finalUrl, LoginResponseObject.class, null, new Response.Listener<LoginResponseObject>()
            {
                @Override
                public void onResponse(LoginResponseObject response)
                {
                    pDialog.dismiss();
                    String authKey = "";
                    if(response.getLoginStatus().contentEquals(MyConfig.LOGIN_FAILED))
                    {
                        Toast.makeText(LoginActivity.this, "Login details not correct", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        authKey = response.getAuthCode();
                        SharedPreferences.Editor editor = prefrences.edit();
                        editor.putString(MyConfig.SHAREDPREFS_AUTHKEY, authKey);
                        editor.commit();

                        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(i);
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed With "+ error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            });

            requestQueue.add(loginRequest);

        }
        else
        {
            Toast.makeText(this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}
