package com.kfive.hopebook.models;

import java.util.Date;

/**
 * Created by apple on 11/5/14.
 */
public class Bookmark {

    //class for bookmark model
//    lets declare our fields
    private int id;
    private int startverse;
    private int endverse;
    private int verse;
    private String versetext;
    private String fullverse;//eg. 1 Chronicles 2 : 17
    private long timestamp;
    private String colortag;
    private int userid;

    public Bookmark() {
    }

    public Bookmark(int startverse, int id, int endverse, int verse,String versetext, String fullverse, long timestamp, String colortag, int userid) {
        this.startverse = startverse;
        this.id = id;
        this.endverse = endverse;
        this.verse = verse;
        this.versetext = versetext;
        this.fullverse = fullverse;
        this.timestamp = timestamp;
        this.colortag = colortag;
        this.userid = userid;
    }

    public String getVersetext() {
        return versetext;
    }

    public void setVersetext(String versetext) {
        this.versetext = versetext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartverse() {
        return startverse;
    }

    public void setStartverse(int startverse) {
        this.startverse = startverse;
    }

    public int getEndverse() {
        return endverse;
    }

    public void setEndverse(int endverse) {
        this.endverse = endverse;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }

    public String getFullverse() {
        return fullverse;
    }

    public void setFullverse(String fullverse) {
        this.fullverse = fullverse;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getColortag() {
        return colortag;
    }

    public void setColortag(String colortag) {
        this.colortag = colortag;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
