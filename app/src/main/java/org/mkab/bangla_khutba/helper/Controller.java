package org.mkab.bangla_khutba.helper;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Controller {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String TAG = "TAG";
    //private static final String WAURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1HNNtGUN9HiM6-ekNGFSMZGsLAON9tozZN6vaORADr6M&sheet=records";
    //private static final String WAURL = "https://script.google.com/macros/s/AKfycbwyutGZCSHgsyaRFxl8H-rmJFNhMh97RHuOnB6t2iLE2TE6ki_E/exec";
    private static final String WAURL = "https://script.google.com/macros/s/AKfycbw4TOxaheyOI3In0ce2sPkEKc3bflxTBtJ-9W-BDqYYNSEoaYk/exec";


    // public static final String WAURL="Your Script Web APP URL";
    // EG : https://script.google.com/macros/s/AKfycbwXXXXXXXXXXXXXXXXX/exec?
    // Make Sure '?' Mark is present at the end of URL
    private static Response response;

    public static JSONObject readAllData() {
        try {
            OkHttpClient client = new OkHttpClient();
            /*Request request = new Request.Builder()
                    .url(WAURL+"?action=readAll")
                    .build();*/


            RequestBody formBody = new FormBody.Builder()
                    .add("action", "readAll")
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }


    public static JSONObject insertData(String id, String name) {
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("action", "insert")
                    .add("id", id)
                    .add("name", name)
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject addItem(String name, String brand) {
        try {
            OkHttpClient client = new OkHttpClient();
/*
            Request request = new Request.Builder()
                    .url(WAURL+"action=addItem"+"&itemName="+name+"&brand="+brand)
                    .build();
            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());*/
            RequestBody formBody = new FormBody.Builder()
                    .add("action", "addItem")
                    .add("itemName", name)
                    .add("brand", brand)
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();
            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());

        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject updateData(String id, String name) {
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("action", "update")
                    .add("id", id)
                    .add("name", name)
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject readData(String id) {
        try {
            OkHttpClient client = new OkHttpClient();
            /*Request request = new Request.Builder()
                    .url(WAURL+"action=read&id="+id)
                    .build();*/

            RequestBody formBody = new FormBody.Builder()
                    .add("action", "read")
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            // Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject deleteData(String id) {
        try {
            OkHttpClient client = new OkHttpClient();
            /*Request request = new Request.Builder()
                    .url(WAURL+"action=delete&id="+id)
                    .build();*/

            RequestBody formBody = new FormBody.Builder()
                    .add("action", "delete")
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url(WAURL)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            // Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }


}