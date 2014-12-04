package com.utopiadevelopers.mnemonicdictionary.networking;

/**
 * Created by satyamkrishna on 04/12/14.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;

public class OkHttpStack extends HurlStack
{
    private final OkHttpClient client;

    public OkHttpStack()
    {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient client)
    {
        if(client == null)
        {
            throw new NullPointerException("Client must not be null.");
        }
        this.client = client;
    }
}