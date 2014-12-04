package com.utopiadevelopers.mnemonicdictionary.json;

/**
 * Created by satyamkrishna on 04/12/14.
 */
import java.util.ArrayList;

public class WordObject
{
    private String definition_short;
    private ArrayList<DefinitionObject> defintion_arr;
    private ArrayList<String> mnemonics_arr;
    private String word;
    private String wordID;

    public String getDefinition_short()
    {
        return this.definition_short;
    }

    public void setDefinition_short(String definition_short)
    {
        this.definition_short = definition_short;
    }

    public ArrayList<DefinitionObject> getDefintionArr()
    {
        return this.defintion_arr;
    }

    public void setDefintion_arr(ArrayList<DefinitionObject> defintion_arr)
    {
        this.defintion_arr = defintion_arr;
    }

    public ArrayList<String> getMnemonics_arr()
    {
        return this.mnemonics_arr;
    }

    public void setMnemonics_arr(ArrayList<String> mnemonics_arr)
    {
        this.mnemonics_arr = mnemonics_arr;
    }

    public String getWord()
    {
        return this.word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getWordID()
    {
        return this.wordID;
    }

    public void setWordID(String wordID)
    {
        this.wordID = wordID;
    }
}
