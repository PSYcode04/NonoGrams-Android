package com.example.nonograms;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SearchImage searchImageFragment;
    private Board boardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchImageFragment = (SearchImage)getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        boardFragment = (Board)getSupportFragmentManager().findFragmentById(R.id.fragment_main2);

    }

    public void searchImage(Bitmap bitmap) {
        boardFragment.setImage(bitmap);
    }
}