package com.example.a9476.newsapp;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a9476 on 2016/8/3.
 */
public class HttpJson extends Thread {
    private String url;
    private AndroidRecyclerViewAdapter androidRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();


    public HttpJson(String url, AndroidRecyclerViewAdapter androidRecyclerViewAdapter, Handler handler, RecyclerView recyclerView){
        this.url=url;
        this.androidRecyclerViewAdapter = androidRecyclerViewAdapter;
        this.handler=handler;
        this.recyclerView=recyclerView;
    }


    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String string;
            while ((string=reader.readLine())!=null);{
                buffer.append(string);
            }
            final List<AndroidResults> androidResultsList = parseJson(buffer.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    androidRecyclerViewAdapter.setData(androidResultsList);
                    recyclerView.setAdapter(androidRecyclerViewAdapter);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<AndroidResults> parseJson(String json){
        try {
            JSONObject object = new JSONObject(json);
            List<AndroidResults> androidResultsList = new ArrayList<>();
            List<IosResults> iosResultsList = new ArrayList<>();
            Boolean error = object.getBoolean("false");
            if (error==false){
                JSONArray objectJSONArray = object.getJSONArray("results");
                for (int i=0;i<objectJSONArray.length();i++){
                    JSONObject resultObject = objectJSONArray.getJSONObject(i);
                    String type = resultObject.getString("type");
                    String desc = resultObject.getString("desc");
                    String author = resultObject.getString("who");
                    String url = resultObject.getString("url");

                    //如果type="Android"，就将获取到的数据设置到实例化的AndroidResults中
                    if (type=="Android"){
                        AndroidResults androidResults = new AndroidResults();
                        androidResultsList.add(androidResults);
                        androidResults.setTitle(desc);
                        androidResults.setAuthor(author);
                        androidResults.setUrl(url);
                    }else if (type=="IOS"){
                        IosResults iosResults = new IosResults();
                        iosResultsList.add(iosResults);
                        iosResults.setTitle(desc);
                        iosResults.setAuthor(author);
                        iosResults.setUrl(url);
                    }
                }
            }
            return androidResultsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }return null;
    }
}
