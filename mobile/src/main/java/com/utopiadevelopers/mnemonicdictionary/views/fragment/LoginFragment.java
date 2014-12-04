package com.utopiadevelopers.mnemonicdictionary.views.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.utopiadevelopers.mnemonicdictionary.R;

/**
 * Created by satyamkrishna on 04/12/14.
 */
public class LoginFragment extends Fragment implements View.OnClickListener
{
    LoginListener activityCallback;

    public interface LoginListener
    {
        public void normalLoginWith(String email,String password);
    }


    private EditText emailET;
    private EditText passwordET;
    private Button loginBT;
    private Button cancelBT;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            activityCallback = (LoginListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement SignUpListenerListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment_login_in,container,false);

        emailET    = (EditText) v.findViewById(R.id.editTextEmail);
        passwordET = (EditText) v.findViewById(R.id.editTextPassword);
        loginBT    = (Button) v.findViewById(R.id.buttonLogin);
        cancelBT   = (Button) v.findViewById(R.id.buttonCancel);

        loginBT.setOnClickListener(this);
        cancelBT.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonLogin:

                String email_val = emailET.getText().toString();
                String password_val = passwordET.getText().toString();



                break;

            case R.id.buttonCancel:
                break;
        }
    }
}
