package com.utopiadevelopers.mnemonicdictionary.json;

/**
 * Created by satyamkrishna on 04/12/14.
 */

import java.util.ArrayList;

public class DefinitionObject
{
    private String def;
    private ArrayList<String> sent;
    private ArrayList<String> syn;

    public String getDefinition()
    {
        return this.def;
    }

    public void setDefinition(String def)
    {
        this.def = def;
    }

    public ArrayList<String> getSentence()
    {
        return this.sent;
    }

    public void setSentence(ArrayList<String> sent)
    {
        this.sent = sent;
    }

    public ArrayList<String> getSynonym()
    {
        return this.syn;
    }

    public void setSynonym(ArrayList<String> syn)
    {
        this.syn = syn;
    }
}