package com.utopiadevelopers.mnemonicdictionary.views.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.utopiadevelopers.mnemonicdictionary.R;
import com.utopiadevelopers.mnemonicdictionary.helpers.CommonLib;

/**
 * Created by satyamkrishna on 04/12/14.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener
{
    SignUpListener activityCallback;

    public interface SignUpListener
    {
        public void onButtonClick(View v);
    }

    Button facebookButton;
    Button googleButton;
    Button emailButton;
    Button loginButton;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            activityCallback = (SignUpListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement SignUpListenerListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login_sign_up,container,false);

        facebookButton = (Button) v.findViewById(R.id.button_sign_facebook);
        googleButton   = (Button) v.findViewById(R.id.button_sign_google);
        emailButton    = (Button) v.findViewById(R.id.button_sign_email);
        loginButton    = (Button) v.findViewById(R.id.button_login);


        facebookButton.setOnClickListener(this);
        googleButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v)
    {
        activityCallback.onButtonClick(v);
    }

    private void logFragment(String message)
    {
        CommonLib.ulog("SignUpFragment",message);
    }
}
