package com.utopiadevelopers.mnemonicdictionary.database;

/**
 * Created by satyamkrishna on 04/12/14.
 */


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class MySQLiteHelper extends SQLiteOpenHelper
{
    private SQLiteDatabase dbHelper;

    private static final String DATABASE_NAME = "utopia_gre.db";
    private static final int DATABASE_VERSION = 1;

    public static final boolean UPDATE_WITHOUT_TOGGLE = false;
    public static final boolean UPDATE_WITH_TOGGLE = true;

    public static final int MARK = 1;
    public static final int UNMARK = 0;

    public static final String TABLE_WORDS = "word_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SERVER_WORDID = "server_word_id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DEF_SHORT = "definition_short";
    public static final String COLUMN_IS_FAV = "is_fav";
    public static final String COLUMN_IS_FAV_DIRTY = "is_fav_dirty";
    public static final String COLUMN_FAV_TIMESTAMP = "fav_timestamp";
    public static final String COLUMN_IS_IGNORE = "is_ignore";
    public static final String COLUMN_IS_IGNORE_DIRTY = "is_ignore_dirty";
    public static final String COLUMN_IGNORE_TIMESTAMP = "ignore_timestamp";
    public static final String COLUMN_IS_HISTORY = "is_history";
    public static final String COLUMN_IS_HISTORY_DIRTY = "is_history_dirty";
    public static final String COLUMN_HISTORY_TIMESTAMP = "history_timestamp";

    // Database creation sql statement
    private final String DATABASE_WORD_LIST = "create table " + TABLE_WORDS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SERVER_WORDID + " integer," + COLUMN_WORD + " text not null," + COLUMN_DEF_SHORT + " text not null," + COLUMN_IS_FAV +
            " TINYINT DEFAULT 0," + COLUMN_IS_FAV_DIRTY + " TINYINT DEFAULT 0," + COLUMN_FAV_TIMESTAMP + " DATETIME," + COLUMN_IS_IGNORE +
            " TINYINT DEFAULT 0," + COLUMN_IS_IGNORE_DIRTY + " TINYINT DEFAULT 0," + COLUMN_IGNORE_TIMESTAMP + " DATETIME," + COLUMN_IS_HISTORY +
            " TINYINT DEFAULT 0," + COLUMN_IS_HISTORY_DIRTY + " TINYINT DEFAULT 0," + COLUMN_HISTORY_TIMESTAMP + " DATETIME" + ");";

    public static final String TABLE_DEFINITION_WORD_LIST = "definition_word_list";
    public static final String COLUMN_WORD_ID = "_wordID";
    public static final String COLUMN_DEFINITION = "definition";
    public static final String COLUMN_DEFINITION_ID = "_definitionID";

    private final String DATABASE_DEFINITION_WORD_LIST = "create table " + TABLE_DEFINITION_WORD_LIST + "(" + COLUMN_DEFINITION_ID
            + " integer primary key autoincrement, " + COLUMN_WORD_ID + " integer, " + COLUMN_DEFINITION + " text not null"
            + ", FOREIGN KEY(" + COLUMN_WORD_ID + ") REFERENCES " + TABLE_WORDS + "(" + COLUMN_ID + ") );";

    public static final String TABLE_MNEMONICS_WORD_LIST = "mnemonics_word_list";
    public static final String COLUMN_MNEMONICS = "mnemonics";

    private final String DATABASE_MNEMONICS_WORD_LIST = "create table " + TABLE_MNEMONICS_WORD_LIST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_WORD_ID + " integer, " + COLUMN_MNEMONICS + " text not null"
            + ", FOREIGN KEY(" + COLUMN_WORD_ID + ") REFERENCES " + TABLE_WORDS + "(" + COLUMN_ID + ") );";

    public static final String TABLE_SENTENCE_WORD_LIST = "sentence_word_list";
    public static final String COLUMN_SENTENCE = "sentence";

    private final String DATABASE_SENTENCE_WORD_LIST = "create table " + TABLE_SENTENCE_WORD_LIST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DEFINITION_ID + " integer, " + COLUMN_SENTENCE + " text not null"
            + ", FOREIGN KEY(" + COLUMN_DEFINITION_ID + ") REFERENCES " + TABLE_DEFINITION_WORD_LIST + "(" + COLUMN_DEFINITION_ID + ") );";

    public static final String TABLE_SYNONYM_WORD_LIST = "synonym_word_list";
    public static final String COLUMN_SYNONYM = "synonym";

    private final String DATABASE_SYNONYM_WORD_LIST = "create table " + TABLE_SYNONYM_WORD_LIST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_DEFINITION_ID + " integer, " + COLUMN_SYNONYM + " text not null"
            + ", FOREIGN KEY(" + COLUMN_DEFINITION_ID + ") REFERENCES " + TABLE_DEFINITION_WORD_LIST + "(" + COLUMN_DEFINITION_ID + ") );";

	/*
     * 		Open , Close , Transaction Methods
	 */

    private static MySQLiteHelper sqliteHelper;

    public static synchronized MySQLiteHelper sharedDatabaseHelper(Context context)
    {
        if(sqliteHelper == null)
           sqliteHelper = new MySQLiteHelper(context);
        return sqliteHelper;
    }


    private MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open()
    {
        dbHelper = getWritableDatabase();
    }

    public SQLiteDatabase getDatabase()
    {
        return dbHelper;
    }

    public void close()
    {
        dbHelper.close();
    }

    public void startTransaction()
    {
        dbHelper.beginTransaction();
    }

    public void commitTransaction()
    {
        dbHelper.setTransactionSuccessful();
    }

    public void endTransaction()
    {
        dbHelper.endTransaction();
    }

	/*
	 * 		Fetching Methods
	 */

    public Cursor fetchWordBasicInfo(String wordID)
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_SERVER_WORDID, wordID));
        return fetchAllWordWithParam(params);
    }

    public Cursor fetchWordMnemonic(String wordID)
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_WORD_ID, wordID));
        return fetchData(TABLE_MNEMONICS_WORD_LIST, new String[]{COLUMN_MNEMONICS}, params);
    }

    public Cursor fetchWordDefinition(String wordID)
    {
        String SQL = "SELECT * FROM " + TABLE_DEFINITION_WORD_LIST
                + " d LEFT JOIN ( SELECT " + COLUMN_DEFINITION_ID + ",GROUP_CONCAT(" + COLUMN_SYNONYM + ") as " + COLUMN_SYNONYM + " FROM " + TABLE_SYNONYM_WORD_LIST + " GROUP BY " + COLUMN_DEFINITION_ID + ") s ON d." + COLUMN_DEFINITION_ID + " = s." + COLUMN_DEFINITION_ID +
                " LEFT JOIN ( SELECT " + COLUMN_DEFINITION_ID + ",GROUP_CONCAT(" + COLUMN_SENTENCE + ") as " + COLUMN_SENTENCE + " FROM " + TABLE_SENTENCE_WORD_LIST + " GROUP BY " + COLUMN_DEFINITION_ID + ") t ON d." + COLUMN_DEFINITION_ID + " = t." + COLUMN_DEFINITION_ID +
                " WHERE " + COLUMN_WORD_ID + "=" + wordID;
        return dbHelper.rawQuery(SQL, null);
    }

    public Cursor fetchAllWord()
    {
        return fetchAllWordWithParam(null);
    }

    public Cursor fetchWordSuggestions(String word)
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_WORD, word, true, true));
        Map<String, String> projectionMap = new HashMap<String, String>();
        projectionMap.put(COLUMN_WORD, COLUMN_WORD + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
        projectionMap.put(COLUMN_ID, COLUMN_ID);
        projectionMap.put(COLUMN_IS_FAV, COLUMN_IS_FAV);
        projectionMap.put(COLUMN_SERVER_WORDID, COLUMN_SERVER_WORDID);
        projectionMap.put(COLUMN_DEF_SHORT, COLUMN_DEF_SHORT + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
        projectionMap.put(COLUMN_SERVER_WORDID, COLUMN_SERVER_WORDID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        return fetchAllWordWithParam(params, true, projectionMap);
    }

    public Cursor fetchAllWordWithoutIgnores()
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_IS_IGNORE, "0"));
        return fetchAllWordWithParam(params);
    }

    public Cursor fetchAllFav()
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_IS_FAV, "1"));
        return fetchAllWordWithParam(params);
    }

    public Cursor fetchAllIgn()
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_IS_IGNORE, "1"));
        return fetchAllWordWithParam(params);
    }

    public Cursor fetchAllHis()
    {
        ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
        params.add(new SelectionRule(COLUMN_IS_HISTORY, "1"));
        return fetchAllWordWithParam(params);
    }

    public Cursor fetchData(String table, String[] Columns, ArrayList<SelectionRule> params)
    {
        boolean paramProvided = false;

        if (params != null)
        {
            if (params.size() > 0)
            {
                paramProvided = true;
            }
        }

        if (!paramProvided)
        {
            return dbHelper.query(table, Columns, null, null, null, null, null);
        } else
        {
            return dbHelper.query(table, Columns, SelectionRule.getSelection(params), SelectionRule.getSelectionArgs(params), null, null, null);
        }

    }

    public Cursor fetchAllWordWithParam(ArrayList<SelectionRule> params, String[] COLUMNS, boolean useProjectionMap, Map<String, String> projectionMap)
    {
        boolean paramProvided = false;

        if (params != null)
        {
            if (params.size() > 0)
            {
                paramProvided = true;
            }
        }

        if (!useProjectionMap)
        {
            if (!paramProvided)
            {
                return dbHelper.query(TABLE_WORDS, COLUMNS, null, null, null, null, null);
            } else
            {
                return dbHelper.query(TABLE_WORDS, COLUMNS, SelectionRule.getSelection(params), SelectionRule.getSelectionArgs(params), null, null, null);
            }
        } else
        {
            SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
            builder.setProjectionMap(projectionMap);
            builder.setTables(TABLE_WORDS);
            if (!paramProvided)
            {
                return builder.query(dbHelper, COLUMNS, null, null, null, null, null);
            } else
            {
                return builder.query(dbHelper, COLUMNS, SelectionRule.getSelection(params), SelectionRule.getSelectionArgs(params), null, null, null);
            }
        }

    }

    public Cursor fetchAllWordWithParam(ArrayList<SelectionRule> params, boolean useProjectionMap, Map<String, String> projectionMap)
    {
        String[] COLUMNS = {COLUMN_ID, COLUMN_SERVER_WORDID, COLUMN_WORD, COLUMN_DEF_SHORT, COLUMN_IS_FAV};
        return fetchAllWordWithParam(params, COLUMNS, useProjectionMap, projectionMap);
    }

    public Cursor fetchAllWordWithParam(ArrayList<SelectionRule> params)
    {
        String[] COLUMNS = {COLUMN_ID, COLUMN_SERVER_WORDID, COLUMN_WORD, COLUMN_DEF_SHORT, COLUMN_IS_FAV};
        return fetchAllWordWithParam(params, COLUMNS, false, null);
    }


	/*
	 *
	 * 		Update Methods
	 *
	 */

    public int updateFav(String serverID, boolean toggle)
    {
        int fav = 1, rows = 0;
        if (!toggle)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_FAV, fav);
            cv.put(COLUMN_FAV_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        } else
        {
            ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
            params.add(new SelectionRule(COLUMN_SERVER_WORDID, serverID));
            Cursor c = fetchAllWordWithParam(params, new String[]{COLUMN_IS_FAV_DIRTY, COLUMN_IS_FAV, COLUMN_SERVER_WORDID}, false, null);
            c.moveToFirst();
            int dirty = c.getInt(c.getColumnIndex(COLUMN_IS_FAV_DIRTY));
            int fav_vl = c.getInt(c.getColumnIndex(COLUMN_IS_FAV));
            c.close();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_FAV_DIRTY, toggle(dirty));
            cv.put(COLUMN_IS_FAV, toggle(fav_vl));
            cv.put(COLUMN_FAV_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        }
        return rows;
    }

    public int updateIgnore(String serverID, boolean toggle)
    {
        int ignore = 1, rows = 0;
        if (!toggle)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_IGNORE, ignore);
            cv.put(COLUMN_IGNORE_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        } else
        {
            ArrayList<SelectionRule> params = new ArrayList<SelectionRule>();
            params.add(new SelectionRule(COLUMN_SERVER_WORDID, serverID));
            Cursor c = fetchAllWordWithParam(params, new String[]{COLUMN_IS_IGNORE_DIRTY, COLUMN_IS_IGNORE, COLUMN_SERVER_WORDID}, false, null);
            c.moveToFirst();
            int dirty = c.getInt(c.getColumnIndex(COLUMN_IS_IGNORE_DIRTY));
            int ign_vl = c.getInt(c.getColumnIndex(COLUMN_IS_IGNORE));
            c.close();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_IGNORE_DIRTY, toggle(dirty));
            cv.put(COLUMN_IS_IGNORE, toggle(ign_vl));
            cv.put(COLUMN_IGNORE_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        }
        return rows;

    }

    public int updateHistory(String serverID, boolean mark)
    {
        int history = 1, rows = 0;
        if (!mark)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_HISTORY, history);
            cv.put(COLUMN_HISTORY_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        } else
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IS_HISTORY_DIRTY, 1);
            cv.put(COLUMN_IS_HISTORY, 1);
            cv.put(COLUMN_HISTORY_TIMESTAMP, getDateTime());
            rows = dbHelper.update(TABLE_WORDS, cv, COLUMN_SERVER_WORDID + " = ?", new String[]{serverID});
        }
        return rows;
    }

	/*
	 *  Helper Functions for DBHelper
	 */

    private String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private int toggle(int value)
    {
        if (value == 0)
            return 1;
        else
            return 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_WORD_LIST);
        db.execSQL(DATABASE_DEFINITION_WORD_LIST);
        db.execSQL(DATABASE_MNEMONICS_WORD_LIST);
        db.execSQL(DATABASE_SENTENCE_WORD_LIST);
        db.execSQL(DATABASE_SYNONYM_WORD_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINITION_WORD_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MNEMONICS_WORD_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENTENCE_WORD_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNONYM_WORD_LIST);
        onCreate(db);
    }
}
