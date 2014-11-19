package com.kfive.hopebook.models;

/**
 * Created by apple on 11/15/14.
 */
public class MoreBibleVersions {
    //    lets declare our fields
    private int id;
    private String version;
    private String language;
    private String license;
    private String price;
    private String dlink;
    private String abbreviation;
    private String info_url;

    public MoreBibleVersions(int id, String version, String language, String license, String price, String dlink, String abbreviation, String info_url) {
        this.id = id;
        this.version = version;
        this.language = language;
        this.license = license;
        this.price = price;
        this.dlink = dlink;
        this.abbreviation = abbreviation;
        this.info_url = info_url;
    }
    public MoreBibleVersions(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDlink() {
        return dlink;
    }

    public void setDlink(String dlink) {
        this.dlink = dlink;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }
}
