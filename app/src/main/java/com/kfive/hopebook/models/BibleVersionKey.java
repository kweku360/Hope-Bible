package com.kfive.hopebook.models;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class BibleVersionKey {
    //class for bible version key model
//    lets declare our fields
    private int id;
    private String table;
    private String abbreviation;
    private String language;
    private String version;
    private String info_text;
    private String info_url;
    private String publisher;
    private String copyright;
    private String copyright_info;

    //constructors


    public BibleVersionKey() {
    }

    public BibleVersionKey(int id,String table, String abbreviation, String version, String language, String info_text, String info_url, String copyright_info, String copyright, String publisher) {
        super();
        this.id = id;
        this.table = table;
        this.abbreviation = abbreviation;
        this.version = version;
        this.language = language;
        this.info_text = info_text;
        this.info_url = info_url;
        this.copyright_info = copyright_info;
        this.copyright = copyright;
        this.publisher = publisher;
    }

    //getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyright_info() {
        return copyright_info;
    }

    public void setCopyright_info(String copyright_info) {
        this.copyright_info = copyright_info;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInfo_text() {
        return info_text;
    }

    public void setInfo_text(String info_text) {
        this.info_text = info_text;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }







}
