package com.utopiadevelopers.mnemonicdictionary;

/**
 * Created by satyamkrishna on 04/12/14.
 */

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.utopiadevelopers.mnemonicdictionary.helpers.CommonLib;
import com.utopiadevelopers.mnemonicdictionary.networking.OkHttpStack;

public class UtopiaApplication extends Application
{

    private RequestQueue requestQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this, new OkHttpStack());
    }

    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }


}
