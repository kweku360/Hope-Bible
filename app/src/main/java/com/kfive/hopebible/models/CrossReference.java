package com.kfive.hopebible.models;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class CrossReference {
    //class for bible version key model
//    lets declare our fields
    private int vid;
    private String r;
    private String sv;
    private String ev;

    //constructors
    public CrossReference() {

    }

    public CrossReference(int vid, String r, String ev, String sv) {
        super();
        this.vid = vid;
        this.r = r;
        this.ev = ev;
        this.sv = sv;
    }

    //Getters and setter

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }
}
