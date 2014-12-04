package com.utopiadevelopers.mnemonicdictionary.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.utopiadevelopers.mnemonicdictionary.R;
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

    }
}
