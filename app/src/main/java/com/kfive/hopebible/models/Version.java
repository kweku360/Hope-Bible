package com.kfive.hopebible.models;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class Version {
    //class for bible version model
    //    lets declare our fields
    private int id;
    private int b;
    private int c;
    private int v;
    private String t;

//constructor
    public Version() {
    }

    public Version(int id, int b, int c, int v, String text) {
        this.id = id;
        this.b = b;
        this.c = c;
        this.v = v;
        this.t = text;
    }

    //Getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
