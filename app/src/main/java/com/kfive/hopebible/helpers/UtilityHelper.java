package com.kfive.hopebible.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.kfive.hopebible.R;
import com.kfive.hopebible.data.BibleVersionKeyHelper;
import com.kfive.hopebible.models.BibleVersionKey;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A set of Utility Clases
 *
 * Created by kfive on 3/10/2016.
 */
public class UtilityHelper {
    Context con;

    public UtilityHelper(Context con) {
        this.con = con;
    }

    public boolean assetCopy(String destinationfolder ,String filename) {
        try {
            //Open your local db as the input stream
            InputStream myInput = con.getAssets().open(filename);

            // Path to the just created empty db
            //String outFileName = DB_PATH + DB_NAME;
            String outFileName = destinationfolder +File.separator+ filename;
            Log.v("destination folder", destinationfolder);
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);

            }
            Log.v("Util - File Copied", "file - " + filename);
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (IOException e){
            Log.v("Util - Unable To Copy", "file - " + filename);
            e.printStackTrace();
            return false;
        }
         return  true;
    }

    //copies downloaded file from storage into databases folder for attachment
    public boolean DbFileCopy(String SourceFolder ,String filename) {
        try {
            //Open your local db as the input stream
            String inFileName = SourceFolder +File.separator+ filename;
            Log.v("Source folder", inFileName);
            InputStream myInput = new FileInputStream(inFileName);

            //A little Hack
            //Open your local db as the input stream

            // Path to the just created empty db
            //String outFileName = DB_PATH + DB_NAME;
            String outFileName = "/data/data/com.kfive.hopebible/databases/"+filename;
            Log.v("destination folder", outFileName);
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);

            }
            Log.v("Coppied Successfully", "file");
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
         return  true;
    }

    public boolean unpackZip(String path, String zipname)
    {
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            Log.v("util - zipfile ", zipname);
            is = new FileInputStream(path+zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                // zapis do souboru
                filename = ze.getName();
                Log.v("util - zipfilename", filename);
                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);

                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            Log.v("Util - Unable To Unzip", "file - " );
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void ListFilesInDir(String path){
        Log.d("List Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("List Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.v("List Files in ", "FileName:" + files[i].getName());
        }
    }
    public boolean deleteFile(String path,String name){
        try{
            File file = new File(path+File.separator+name);
            file.delete();
            Log.v("Deleted file", name);
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void SaveCurrentVersion(Cursor checkedItem){
        SharedPreferences sharedPref = con.getSharedPreferences(
                con.getString(R.string.bibleversionpref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("currentversion", checkedItem.getInt(1)); //this gets the value on the db table id
        editor.putInt("currentversioncount", checkedItem.getInt(0)); //this gets the count value
        editor.commit();
    }
    public String getCurrentDBName(){
        SharedPreferences sharedPref = con.getSharedPreferences(
                con.getString(R.string.bibleversionpref), Context.MODE_PRIVATE);
        int defaultValue = 1;
        int version = sharedPref.getInt("currentversion", defaultValue);

        BibleVersionKeyHelper bibleVersionKeyHelper = new BibleVersionKeyHelper(con);
        BibleVersionKey bibleVersionKey = bibleVersionKeyHelper.findOne(version);

        //Todo : Verify if table actually exists as a db and can be opened
        //TODO : If Not REdirect to a proper table and send toast if possible

        return bibleVersionKey.getTable();
    }

}
