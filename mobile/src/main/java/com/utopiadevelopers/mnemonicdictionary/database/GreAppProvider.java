package com.utopiadevelopers.mnemonicdictionary.database;

/**
 * Created by satyamkrishna on 04/12/14.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.utopiadevelopers.mnemonicdictionary.helpers.MyConfig;
import com.utopiadevelopers.mnemonicdictionary.json.WordObject;

public class GreAppProvider extends ContentProvider
{
    // Used for debugging and logging
    private static final String TAG = "GreAppProvider";
    // The incoming URI matches the Insert All Words URI pattern
    private static final int INSERT_ALL_WORDS = 1;
    private static final int FETCH_ALL_WORDS = 2;
    private static final int FETCH_ALL_FAV = 3;
    private static final int FETCH_ALL_IGN = 4;
    private static final int FETCH_ALL_HIS = 5;
    private static final int FETCH_ALL_WORDS_WITHOUT_IGNORED = 6;
    private static final int INSERT_FIRST_SYNC_FAV = 7;
    private static final int INSERT_FIRST_SYNC_IGN = 8;
    private static final int INSERT_FIRST_SYNC_HIS = 9;
    private static final int TOGGLE_FAV = 10;
    private static final int TOGGLE_IGN = 11;
    private static final int MARK_HIS = 12;
    private static final int SEARCH_SUGGEST = 13;
    private static final int FETCH_WORD_BASIC_INFO = 14;
    private static final int FETCH_WORD_MNEMONIC = 15;
    private static final int FETCH_WORD_DEFINITION = 16;
    private static final UriMatcher sUriMatcher;

    private MySQLiteHelper dbHelper;

    static
    {

		/*
		 * Creates and initializes the URI matcher
		 */
        // Create a new instance
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_INSERT_ALL_WORDS, INSERT_ALL_WORDS);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_ALL_WORDS, FETCH_ALL_WORDS);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_ALL_FAV, FETCH_ALL_FAV);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_ALL_IGN, FETCH_ALL_IGN);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_ALL_HIS, FETCH_ALL_HIS);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_ALL_WORDS_WITHOUT_IGNORED, FETCH_ALL_WORDS_WITHOUT_IGNORED);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_MATCH_FIRST_SYNC_FAV, INSERT_FIRST_SYNC_FAV);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_MATCH_FIRST_SYNC_IGN, INSERT_FIRST_SYNC_IGN);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_MATCH_FIRST_SYNC_HIS, INSERT_FIRST_SYNC_HIS);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_TOGGLE_FAV,TOGGLE_FAV);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_TOGGLE_IGN,TOGGLE_IGN);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_MARK_HIST,MARK_HIS);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_BASIC_INFO,FETCH_WORD_BASIC_INFO);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_BASIC_INFO + "/*",FETCH_WORD_BASIC_INFO);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_MNEMONIC,FETCH_WORD_MNEMONIC);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_MNEMONIC + "/*",FETCH_WORD_MNEMONIC);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_DEFINITION,FETCH_WORD_DEFINITION);
        sUriMatcher.addURI(MyConfig.AUTHORITY, MyConfig.URI_FETCH_WORD_DEFINITION + "/*",FETCH_WORD_DEFINITION);
        sUriMatcher.addURI(MyConfig.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        sUriMatcher.addURI(MyConfig.AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
    }

    @Override
    public boolean onCreate()
    {
        dbHelper = MySQLiteHelper.sharedDatabaseHelper(getContext());
        dbHelper.open();
        return true;
    }

    /**
     * This method is called when a client calls
     * {@link android.content.ContentResolver#query(Uri, String[], String, String[], String)}
     * . Queries the database and returns a cursor containing the results.
     *
     * @return A cursor containing the results of the query. The cursor exists
     *         but is empty if the query returns no results or an exception
     *         occurs.
     * @throws IllegalArgumentException
     *             if the incoming URI pattern is invalid. *
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        switch (sUriMatcher.match(uri))
        {

            case FETCH_WORD_BASIC_INFO:
                return dbHelper.fetchWordBasicInfo(uri.getLastPathSegment());
            case FETCH_WORD_MNEMONIC:
                return dbHelper.fetchWordMnemonic(uri.getLastPathSegment());
            case FETCH_WORD_DEFINITION:
                return dbHelper.fetchWordDefinition(uri.getLastPathSegment());
            case FETCH_ALL_WORDS:
                return dbHelper.fetchAllWord();
            case FETCH_ALL_FAV:
                return dbHelper.fetchAllFav();
            case FETCH_ALL_IGN:
                return dbHelper.fetchAllIgn();
            case FETCH_ALL_HIS:
                return dbHelper.fetchAllHis();
            case FETCH_ALL_WORDS_WITHOUT_IGNORED:
                return dbHelper.fetchAllWordWithoutIgnores();
            case SEARCH_SUGGEST:
                return dbHelper.fetchWordSuggestions("%"+uri.getLastPathSegment().toLowerCase()+"%");

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        int numValues = 0;
        if(values!=null)
        {
            numValues = values.length;
        }

        dbHelper.startTransaction();

        switch (sUriMatcher.match(uri))
        {
            case INSERT_ALL_WORDS:
                WordDataSource wordDataSourceHandler = new WordDataSource(getContext(), dbHelper.getDatabase());
                DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                SharedPreferences preferences = getContext().getSharedPreferences(MyConfig.SHAREDPREFS, Context.MODE_PRIVATE);
                long download_id = preferences.getLong(MyConfig.SHAREDPREFS_DOWNLOAD_ID, -1);

                ParcelFileDescriptor file;
                wordDataSourceHandler.setUpTranscation();
                try
                {
                    file = manager.openDownloadedFile(download_id);
                    FileInputStream fileInputStream = new ParcelFileDescriptor.AutoCloseInputStream(file);
                    numValues = parseFileInputStream(fileInputStream,wordDataSourceHandler);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        dbHelper.commitTransaction();
        dbHelper.endTransaction();
        return numValues;
    }

    /**
     * This is called when a client calls
     * {@link android.content.ContentResolver#getType(Uri)}. Returns the MIME
     * data type of the URI given as a parameter.
     *
     * @param uri
     *            The URI whose MIME type is desired.
     * @return The MIME type of the URI.
     * @throws IllegalArgumentException
     *             if the incoming URI pattern is invalid.
     */
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        switch (sUriMatcher.match(uri))
        {
            case INSERT_FIRST_SYNC_FAV:
                dbHelper.updateFav(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITHOUT_TOGGLE);
                break;
            case INSERT_FIRST_SYNC_IGN:
                dbHelper.updateIgnore(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITHOUT_TOGGLE);
                break;
            case INSERT_FIRST_SYNC_HIS:
                dbHelper.updateHistory(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITHOUT_TOGGLE);
                break;
            case TOGGLE_FAV:
                dbHelper.updateFav(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITH_TOGGLE);
                break;
            case TOGGLE_IGN:
                dbHelper.updateIgnore(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITH_TOGGLE);
                break;
            case MARK_HIS:
                dbHelper.updateHistory(values.getAsString(MySQLiteHelper.COLUMN_SERVER_WORDID),MySQLiteHelper.UPDATE_WITH_TOGGLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }
        return 0;
    }

    public int parseFileInputStream(FileInputStream fileIn, WordDataSource wordDataSourceHandler) throws IOException
    {
        int size = 0;
        JsonReader reader = new JsonReader(new InputStreamReader(fileIn));
        Gson gson = new Gson();
        reader.beginArray();

        while (reader.hasNext())
        {
            WordObject word = gson.fromJson(reader, WordObject.class);
            wordDataSourceHandler.add_word_with_transaction(word);
            size++;
        }
        reader.endArray();
        reader.close();
        return size;
    }

}