package com.utopiadevelopers.mnemonicdictionary.networking;

import com.android.volley.toolbox.HurlStack;

import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by satyamkrishna on 04/12/14.
 */
public class CustomHttpStack extends HurlStack
{
    private final HttpClient client;

    public CustomHttpStack(HttpClient client)
    {
        this.client = client;
    }
}
