package com.kfive.hopebible.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by kweku on 3/25/2017.
 */
@JsonObject
public class MoreVersions {
    @JsonField
    public List<Data> data;

    @JsonObject
    public static class Data{
        @JsonField
        public String type;
        @JsonField
        public String id;
        @JsonField
        public Attributes attributes;

        @JsonObject
        public static class Attributes{
            @JsonField
            public int id;
            @JsonField
            public String versionname;
            @JsonField
            public String bookcode;
            @JsonField
            public String downloadlink;
            @JsonField
            public String imagelink;
            @JsonField
            public int downloadcount;
            @JsonField
            public int filesize;
//            @JsonField
//            public String type;
//            @JsonField
//            public String type;
        }

    }

}

