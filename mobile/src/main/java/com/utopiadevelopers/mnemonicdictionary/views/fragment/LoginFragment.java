package com.utopiadevelopers.mnemonicdictionary.views.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.GoogleAuthUtil;
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
        public void showInitalLoginView();
    }


    private AutoCompleteTextView emailET;
    private AutoCompleteTextView passwordET;
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

        emailET    = (AutoCompleteTextView) v.findViewById(R.id.editTextEmail);
        passwordET = (AutoCompleteTextView) v.findViewById(R.id.editTextPassword);
        loginBT    = (Button) v.findViewById(R.id.buttonLogin);
        cancelBT   = (Button) v.findViewById(R.id.buttonCancel);

        loginBT.setOnClickListener(this);
        cancelBT.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getAccountNames());
        emailET.setAdapter(adapter);

        return v;
    }


    private String[] getAccountNames()
    {
        AccountManager mAccountManager = AccountManager.get(getActivity());
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = accounts[i].name;
        }
        return names;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonLogin:

                String email_val = emailET.getText().toString();
                String password_val = passwordET.getText().toString();

                activityCallback.normalLoginWith(email_val,password_val);

                break;

            case R.id.buttonCancel:
                activityCallback.showInitalLoginView();
                break;
        }
    }
}
