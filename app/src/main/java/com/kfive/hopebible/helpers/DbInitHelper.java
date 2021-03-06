package com.kfive.hopebible.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/26/14.
 * copyright
 */
public class DbInitHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    //The Android's default system path of your application database.
    //private static String DB_PATH = "/data/data/com.kfive.hopebible/databases/";
    private static String DB_PATH = "/data/data/com.kfive.hopebible/databases/";

    private static String DB_NAME = "HopeBook";


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DbInitHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
            Log.v("DBExists", "Db Exists Already");
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                Log.v("dbree", e.toString());
                Log.v("dree", e.toString());
                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {

            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //a few changes here and there
        //first copy zip to app folder
        UtilityHelper utilityHelper = new UtilityHelper(myContext);
        String destfolder = myContext.getFilesDir().getAbsolutePath();
      //  String destfolder = Environment.getExternalStorageDirectory().getPath();
        if (utilityHelper.assetCopy(destfolder, DB_NAME + ".zip") == true) {
            if (utilityHelper.unpackZip(destfolder + File.separator, DB_NAME + ".zip") == true) {


                //Open your local db as the input stream
                //  InputStream myInput = myContext.getAssets().open(DB_NAME);
                InputStream myInput = myContext.openFileInput(DB_NAME);

                // Path to the just created empty db
                //String outFileName = DB_PATH + DB_NAME;
                String outFileName = DB_PATH + DB_NAME;

                //Open the empty db as the output stream
                OutputStream myOutput = new FileOutputStream(outFileName);

                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);

                }
                Log.v("Coppied Successfully", "db Coppied Successfully");
                //Close the streams
                myOutput.flush();
                myOutput.close();
                myInput.close();

                //finally we delete temp files
                utilityHelper.deleteFile(destfolder, DB_NAME + ".zip");
                utilityHelper.deleteFile(destfolder, DB_NAME);


            }
//
        }
        ;


    }

    public void openDataBase() throws SQLException {

        //Open the database
        // String myPath = DB_PATH + DB_NAME;
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}


//            lets lsit all file
//            File f = new File(destfolder+File.separator);
//            String[] someFiles = f.list();
//            for(int c = 0;c<someFiles.length;c++){
//                Log.v("filename", someFiles[c]);
//            }