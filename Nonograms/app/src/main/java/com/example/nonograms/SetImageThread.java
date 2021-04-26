package com.example.nonograms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SetImageThread implements Runnable{
    Bitmap bitmap;
    Bitmap newBitmap;
    Bitmap[] bitmapPiece;
    int[] grayScale;

    @Override
    public void run(){
        String imageUrl = Thread.currentThread().getName();


        try{
            URL url = new URL(imageUrl);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setDoInput(true);
            con.connect();

            InputStream image = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(image);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
