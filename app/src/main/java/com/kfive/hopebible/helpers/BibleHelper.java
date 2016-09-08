package com.kfive.hopebible.helpers;

import android.content.Intent;
import android.database.Cursor;

import com.kfive.hopebible.activities.HbVerses;
import com.kfive.hopebible.adapters.BibleTextAdapter;

import java.util.ArrayList;

/**
 * Created by kweku on 9/8/2016.
 */
public class BibleHelper {
    private int startv,endv;



    public BibleHelper() {
    }

    public int getStartv() {
        return startv;
    }

    public void setStartv(int startv) {
        this.startv = startv;
    }

    public int getEndv() {
        return endv;
    }

    public void setEndv(int endv) {
        this.endv = endv;
    }

    public int[] prevNextChapter(String lever,int verses){
        try {
            String clone; //clone for getting the other verse
            clone = ""+verses;
            if(clone.length() == 7){
                clone = clone.substring(0,4);
            }else{
                clone = clone.substring(0,5);
            }
            if(lever.equals("next")){
                int endverse = Integer.parseInt(clone+"999");
                int[] farr = {verses,endverse};
                //we set these values for later use
                setStartv(verses);
                setEndv(endverse);

                return farr;
            }else{
                int startverse = Integer.parseInt(clone+"001");
                int[] farr = {startverse,verses};
                //we set these values for later use
                setStartv(startverse);
                setEndv(verses);
                return farr;
            }
        }catch (Exception e){
            return  null;
        }
    }

    public int getRowId(BibleTextAdapter bAdapter,String position){
        if(position.equals("next")){
            Cursor cs = (Cursor)bAdapter.getItem(bAdapter.getCount()-1);
            return cs.getInt(0)+1;
        }else{
            //note: we get the end of the last verse id
            Cursor cs = (Cursor)bAdapter.getItem(0);
            return cs.getInt(0)-1;
        }
    }

    public int[] getStartEndVerses(Intent intent){
        ArrayList<String> message = intent.getStringArrayListExtra(HbVerses.EXTRA_MESSAGE);
        int startverse = Integer.parseInt(message.get(1));
        int endverse = Integer.parseInt(message.get(2));
        int verseposition = Integer.parseInt(message.get(3)); //just a good addition
        int[] verses = {startverse,endverse,verseposition};
        //we set these values for later use
        setStartv(startverse);
        setEndv(endverse);
        return verses;
    }




}
