package com.utopiadevelopers.mnemonicdictionary.helpers;

/**
 * Created by satyamkrishna on 03/12/14.
 */

public class MyConfig
{
    public static final String LOGIN_FAILED = "fail";

    public static final String SHAREDPREFS = "MyPrefs";
    public static final String SHAREDPREFS_LOGGED_IN = "loggedin";
    public static final String SHAREDPREFS_AUTHKEY = "authkey";
    public static final String SHAREDPREFS_DOWNLOAD_ID = "json_download-id";
    public static final String SHAREDPREFS_DATABASE_ADDED = "is_database_added";
    public static final String JSON_URL = "http://www.utopiadevelopers.com/gre/upload/O.json";
    public static final String APP_DIRECTORY = "/utopiagre/";

    public static final String API_URL = "http://www.utopiadevelopers.com/gre/api/";
    public static final String API_LOGIN = API_URL + "login.php?";
    public static final String API_GCM_REG = "register_gcm.php?";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public static final String PARAM_USER_PASS = "USER_PASS";
    public static final String LOGIN_TYPE = "login_type";


    // URI FOR CONTENT PROVIDERS - START
    public static final String AUTHORITY = "com.utopiadevelopers.mnemonicdictionary.provider";
    public static final String URI_BASE = "content://";
    public static final String URI_INSERT_ALL_WORDS = "add_words_all";
    public static final String URI_FETCH_ALL_WORDS = "fetch_words_all";
    public static final String URI_FETCH_ALL_FAV = "fetch_words_fav";
    public static final String URI_FETCH_ALL_IGN = "fetch_words_ign";
    public static final String URI_FETCH_ALL_HIS = "fetch_words_his";
    public static final String URI_FETCH_ALL_WORDS_WITHOUT_IGNORED = "fetch_words_not_ign";
    public static final String URI_MATCH_FIRST_SYNC_FAV = "add_first_sync_fav";
    public static final String URI_MATCH_FIRST_SYNC_IGN = "add_first_sync_ign";
    public static final String URI_MATCH_FIRST_SYNC_HIS = "add_first_sync_his";
    public static final String URI_TOGGLE_FAV = "toggle_fav";
    public static final String URI_MARK_HIST = "mark_hist";
    public static final String URI_TOGGLE_IGN = "toggle_ign";
    public static final String URI_FETCH_WORD_BASIC_INFO = "fetch_word_basic";
    public static final String URI_FETCH_WORD_MNEMONIC = "fetch_word_mnemonic";
    public static final String URI_FETCH_WORD_DEFINITION = "fetch_word_definition";

    // URI FOR CONTENT PROVIDERS - END


    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final static String ARG_FIRST_SYNC = "FIRST_SYNC";
    public final static String ARG_WORD_ID = "WORD_ID";

    public final static String ACCOUNT_TYPE_VAL = "com.utopiadevelopers.gre";
    public final static String AUTH_TYPE_VAL = "All";
    public static String AUTH_TOKEN = "";
    public final static String IS_AUTHENTICATED = "Is Authenticated";

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String SENDER_ID = "191181416637";
    public static final String ARG_IS_GCM_REGISTERED_ON_SERVER = "Is Gcm Registered";
    public static String regid;

    public static final String INSTALLATION = "INSTALLATION";

}