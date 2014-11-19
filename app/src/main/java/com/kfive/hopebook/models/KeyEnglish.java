package com.kfive.hopebook.models;

/**
 * Project: Hope Book
 * Created by kweku kankam on 10/25/14.
 * copyright
 */
public class KeyEnglish {
    //class for Key English model
//    lets declare our fields
    private int b;
    private String n;

    //constructors

    public KeyEnglish() {
    }

    public KeyEnglish(int b, String n) {
        super();
        this.b = b;
        this.n = n;
    }

    //getters and setters

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
