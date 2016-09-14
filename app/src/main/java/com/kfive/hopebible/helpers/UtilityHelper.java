package com.kfive.hopebible.helpers;

import android.content.Context;
import android.util.Log;

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
            Log.v("zipfile", zipname);
            is = new FileInputStream(path+zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                // zapis do souboru
                filename = ze.getName();
                Log.v("zipfilename", filename);
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
            e.printStackTrace();
            return false;
        }

        return true;
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
}
