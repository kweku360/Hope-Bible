package com.kfive.hopebible.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by apple on 11/15/14.
 */
public class MoreBibleVersions extends RealmObject {
    //    lets declare our fields
    @PrimaryKey
    private int id;
    private String versionname;
    private String bookcode;
    private String downloadlink;
    private String imagelink;
    private String aboutlink;
    private int downloadcount;
    private long filesize;
    private String licence;
    private String publisher;


//    public MoreBibleVersions() {
//
//    }
    public MoreBibleVersions(){

    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public int getDownloadcount() {
        return downloadcount;
    }

    public void setDownloadcount(int downloadcount) {
        this.downloadcount = downloadcount;
    }

    public String getAboutlink() {
        return aboutlink;
    }

    public void setAboutlink(String aboutlink) {
        this.aboutlink = aboutlink;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
