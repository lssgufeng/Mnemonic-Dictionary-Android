package com.utopiadevelopers.mnemonicdictionary.database;

/**
 * Created by satyamkrishna on 04/12/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.utopiadevelopers.mnemonicdictionary.json.WordObject;
import com.utopiadevelopers.mnemonicdictionary.json.DefinitionObject;

public class WordDataSource
{
    private SQLiteDatabase database;

    private SQLiteStatement word_stmt;
    private SQLiteStatement def_stmt;
    private SQLiteStatement sent_stmt;
    private SQLiteStatement synm_stmt;
    private SQLiteStatement nmen_stmt;

    public WordDataSource(Context context, SQLiteDatabase database)
    {
        this.database = database;
    }

    public void setUpTranscation()
    {
        // String word_check_sql = "";
        // check_stmt = database.compileStatement(word_check_sql);
        String word_sql = "INSERT INTO " + MySQLiteHelper.TABLE_WORDS + " (" + MySQLiteHelper.COLUMN_SERVER_WORDID + ","
                + MySQLiteHelper.COLUMN_WORD + "," + MySQLiteHelper.COLUMN_DEF_SHORT + ") values (?,?, ?);";
        word_stmt = database.compileStatement(word_sql);
        String def_sql = "INSERT INTO " + MySQLiteHelper.TABLE_DEFINITION_WORD_LIST + " (" + MySQLiteHelper.COLUMN_WORD_ID + ","
                + MySQLiteHelper.COLUMN_DEFINITION + ") values (?, ?);";
        def_stmt = database.compileStatement(def_sql);

        String sent_sql = "INSERT INTO " + MySQLiteHelper.TABLE_SENTENCE_WORD_LIST + " (" + MySQLiteHelper.COLUMN_DEFINITION_ID + ","
                + MySQLiteHelper.COLUMN_SENTENCE + ") values (?, ?);";
        sent_stmt = database.compileStatement(sent_sql);

        String synm_sql = "INSERT INTO " + MySQLiteHelper.TABLE_SYNONYM_WORD_LIST + " (" + MySQLiteHelper.COLUMN_DEFINITION_ID + ","
                + MySQLiteHelper.COLUMN_SYNONYM + ") values (?, ?);";
        synm_stmt = database.compileStatement(synm_sql);

        String nme_sql = "INSERT INTO " + MySQLiteHelper.TABLE_MNEMONICS_WORD_LIST + " (" + MySQLiteHelper.COLUMN_WORD_ID + ","
                + MySQLiteHelper.COLUMN_MNEMONICS + ") values (?, ?);";
        nmen_stmt = database.compileStatement(nme_sql);

    }

    public void add_word_with_transaction(WordObject obj)
    {

        word_stmt.bindString(1, obj.getWordID());
        word_stmt.bindString(2, obj.getWord());
        word_stmt.bindString(3, obj.getDefinition_short());
        long entryID = word_stmt.executeInsert();
        word_stmt.clearBindings();

        for (int i = 0; i < obj.getDefintionArr().size(); i++)
        {
            DefinitionObject data = obj.getDefintionArr().get(i);
            if(data.getDefinition() != null)
            {
                def_stmt.bindLong(1, entryID);
                def_stmt.bindString(2, data.getDefinition());
                long defID = def_stmt.executeInsert();
                def_stmt.clearBindings();
                for (int j = 0; j < data.getSentence().size(); j++)
                {
                    if(data.getSentence().get(j) != null)
                    {
                        sent_stmt.bindLong(1, defID);
                        sent_stmt.bindString(2, data.getSentence().get(j));
                        sent_stmt.executeInsert();
                        sent_stmt.clearBindings();
                    }
                }

                for (int j = 0; j < data.getSynonym().size(); j++)
                {
                    if(data.getSynonym().get(j) != null)
                    {
                        synm_stmt.bindLong(1, defID);
                        synm_stmt.bindString(2, data.getSynonym().get(j));
                        synm_stmt.executeInsert();
                        synm_stmt.clearBindings();
                    }
                }
            }
        }

        for (int i = 0; i < obj.getMnemonics_arr().size(); i++)
        {
            if(obj.getMnemonics_arr().get(i) != null)
            {
                nmen_stmt.bindLong(1, entryID);
                nmen_stmt.bindString(2, obj.getMnemonics_arr().get(i));
                nmen_stmt.executeInsert();
                nmen_stmt.clearBindings();
            }
        }
    }

    public void add_word(WordObject obj)
    {
        ContentValues cv;
        long rowID, defID;

        cv = new ContentValues();
        cv.put(MySQLiteHelper.COLUMN_WORD, obj.getWord());
        cv.put(MySQLiteHelper.COLUMN_DEF_SHORT, obj.getDefinition_short());
        rowID = database.insert(MySQLiteHelper.TABLE_WORDS, null, cv);
        for (int i = 0; i < obj.getDefintionArr().size(); i++)
        {
            DefinitionObject data = obj.getDefintionArr().get(i);
            cv = new ContentValues();
            cv.put(MySQLiteHelper.COLUMN_WORD_ID, rowID);
            cv.put(MySQLiteHelper.COLUMN_DEFINITION, data.getDefinition());
            defID = database.insert(MySQLiteHelper.TABLE_DEFINITION_WORD_LIST, null, cv);

            for (int j = 0; j < data.getSentence().size(); j++)
            {
                cv = new ContentValues();
                cv.put(MySQLiteHelper.COLUMN_DEFINITION_ID, defID);
                cv.put(MySQLiteHelper.COLUMN_SENTENCE, data.getSentence().get(j));
                database.insert(MySQLiteHelper.TABLE_SENTENCE_WORD_LIST, null, cv);
            }

            for (int j = 0; j < data.getSynonym().size(); j++)
            {
                cv = new ContentValues();
                cv.put(MySQLiteHelper.COLUMN_DEFINITION_ID, defID);
                cv.put(MySQLiteHelper.COLUMN_SYNONYM, data.getSynonym().get(j));
                database.insert(MySQLiteHelper.TABLE_SYNONYM_WORD_LIST, null, cv);
            }
        }

        for (int i = 0; i < obj.getMnemonics_arr().size(); i++)
        {
            cv = new ContentValues();
            cv.put(MySQLiteHelper.COLUMN_WORD_ID, rowID);
            cv.put(MySQLiteHelper.COLUMN_MNEMONICS, obj.getMnemonics_arr().get(i));
            database.insert(MySQLiteHelper.TABLE_MNEMONICS_WORD_LIST, null, cv);
        }
    }

}
