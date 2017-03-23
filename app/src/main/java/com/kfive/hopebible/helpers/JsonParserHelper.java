package com.kfive.hopebible.helpers;

import com.kfive.hopebible.models.MoreBibleVersions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by apple on 11/15/14.
 */
public class JsonParserHelper {
    public JsonParserHelper() {
    }

    public static ArrayList<MoreBibleVersions> moreBibleibleVersionJsonParse(String jsonresult){
        ArrayList<MoreBibleVersions> moreBibleVersions = new ArrayList<MoreBibleVersions>();
        try {
            JSONArray moreversions = new JSONArray(jsonresult);
            for (int i=0;i<moreversions.length();i++){
                JSONObject js = moreversions.getJSONObject(i);

                MoreBibleVersions mb = new MoreBibleVersions();
//                mb.setVersion(js.getString("version"));
//                mb.setLanguage(js.getString("language"));
//                mb.setLicense(js.getString("license"));
//                mb.setPrice(js.getString("price"));
//                mb.setDlink(js.getString("dlink"));
//                mb.setAbbreviation(js.getString("abreviation"));
//                mb.setInfo_url(js.getString("infourl"));

                moreBibleVersions.add(mb);
            }

        }catch (JSONException e){

        }


        return moreBibleVersions;

    }
}
