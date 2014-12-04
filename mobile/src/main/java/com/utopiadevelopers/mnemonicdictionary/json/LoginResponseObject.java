package com.utopiadevelopers.mnemonicdictionary.json;

/**
 * Created by satyamkrishna on 04/12/14.
 */

public class LoginResponseObject
{

    private String login, auth,email;

    public String getEmail()
    {
        return email;
    }

    public String getLoginStatus()
    {
        return login;
    }

    public String getAuthCode()
    {
        return auth;
    }
}