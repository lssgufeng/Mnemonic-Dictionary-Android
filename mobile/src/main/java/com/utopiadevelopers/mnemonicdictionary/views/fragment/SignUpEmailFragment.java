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

import com.google.android.gms.auth.GoogleAuthUtil;
import com.utopiadevelopers.mnemonicdictionary.R;

/**
 * Created by satyamkrishna on 04/12/14.
 */
public class SignUpEmailFragment extends Fragment implements View.OnClickListener
{
    SignUpEmailListener activityCallback;

    public interface SignUpEmailListener
    {
        public void showInitalLoginView();
    }

    private AutoCompleteTextView emailET;
    private Button cancelBT;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            activityCallback = (SignUpEmailListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement SignUpListenerListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login_sign_up_with_email,container,false);

        cancelBT   = (Button) v.findViewById(R.id.buttonCancel);
        emailET    = (AutoCompleteTextView) v.findViewById(R.id.editTextEmail);
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
            case R.id.buttonCancel:
                activityCallback.showInitalLoginView();
                break;
        }
    }
}
