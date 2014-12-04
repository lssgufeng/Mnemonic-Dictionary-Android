package com.utopiadevelopers.mnemonicdictionary.database;

/**
 * Created by satyamkrishna on 04/12/14.
 */

import java.util.ArrayList;

public class SelectionRule
{
    private String column;
    String value;
    boolean equal,is_like;

    public SelectionRule(String column,String value)
    {
        this(column,value,true);
    }

    public SelectionRule(String column,String value,boolean equal)
    {
        this(column,value,true,false);
    }

    public SelectionRule(String column,String value,boolean equal,boolean like)
    {
        this.column = column;
        this.value = value;
        this.equal = equal;
        this.is_like = like;
    }

    public String getColumn()
    {
        return column;
    }

    public String getValue()
    {
        return value;
    }

    public void setQueryTypeLike()
    {
        is_like = true;
    }

    public String getEquality()
    {
        if(!is_like)
        {
            if(equal)
            {
                return " =? ";
            }
            else
            {
                return " !=? ";
            }
        }
        else
        {
            if(equal)
            {
                return " LIKE ? ";
            }
            else
            {
                return " NOT LIKE ? ";
            }
        }
    }


    public static String getSelection(ArrayList<SelectionRule> params)
    {
        String selection = "";
        for (SelectionRule rule:params)
        {
            selection = selection + rule.getColumn() + rule.getEquality() +" AND ";
        }
        selection = selection.substring(0, selection.length()-5);
        return selection;
    }

    public static String[] getSelectionArgs(ArrayList<SelectionRule> params)
    {
        String selArgs[] = new String[params.size()];
        int index=0;
        for (SelectionRule rule:params)
        {
            selArgs[index] = rule.getValue();
            index++;
        }
        return selArgs;
    }
}