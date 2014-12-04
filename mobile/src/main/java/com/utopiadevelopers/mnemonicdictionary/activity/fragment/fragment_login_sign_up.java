package com.utopiadevelopers.mnemonicdictionary.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utopiadevelopers.mnemonicdictionary.R;

/**
 * Created by satyamkrishna on 04/12/14.
 */
public class fragment_login_sign_up extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_login_sign_up,container,false);
    }
}
