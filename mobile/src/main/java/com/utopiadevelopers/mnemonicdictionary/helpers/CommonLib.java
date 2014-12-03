package com.utopiadevelopers.mnemonicdictionary.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by satyamkrishna on 03/12/14.
 */
public class CommonLib
{
    public static boolean isUserLoggedIn(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MyConfig.SHAREDPREFS,Context.MODE_MULTI_PROCESS);
        if(preferences.getBoolean(MyConfig.SHAREDPREFS_LOGGED_IN,false))
        {
            return true;
        }
        return false;
    }
}