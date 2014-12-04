package com.utopiadevelopers.mnemonicdictionary.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String getMd5Hash(String input)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
    }

    public static void ulog(String tag,String message)
    {
        Log.d(tag,message);
    }
}
