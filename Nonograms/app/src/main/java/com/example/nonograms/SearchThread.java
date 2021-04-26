package com.example.nonograms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SearchThread implements Runnable{
    String imageUrl = "";
    StringBuilder sb;
    int rNum;

    static String clientId = "JynrtSPKwX56rX5xkU7J";
    static String clientSecret = "SZhb3HLvTG";


    @Override
    public void run(){
        imageUrl = "";
        rNum = 1;
        String text = Thread.currentThread().getName();
        try{
            text = URLEncoder.encode(text, "UTF-8");
        }catch(UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/image?query=" +text+ "&display=1&start=" + rNum + "&";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            con.setDoInput(true);
            con.connect();

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();

            Gson gson = new GsonBuilder().create();
            ImageModel data = gson.fromJson(sb.toString(), ImageModel.class);

            for(ImageItemModel item:data.items){
                imageUrl = item.link;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
