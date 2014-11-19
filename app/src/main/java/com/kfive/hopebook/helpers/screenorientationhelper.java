package com.kfive.hopebook.helpers;

/**
 * Created by apple on 10/31/14.
 */
public class screenorientationhelper {

    public screenorientationhelper() {
    }
     public static String getCurrentOrientation(int orientation){
         if(orientation == 1){
             return "Portrait";
         }
         if (orientation == 2){
             return "Landscape";
         }

         return "null";

     }


}
