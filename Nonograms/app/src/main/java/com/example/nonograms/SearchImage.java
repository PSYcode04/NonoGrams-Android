package com.example.nonograms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SearchImage extends Fragment {
    MainActivity activity;

    String typeQuery;
    EditText editText;
    Button searchButton;
    Button galleryButton;
    Uri selectedImageUri;
    Bitmap bitmap;

    SearchThread t1;
    SetImageThread t2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_image, container, false);
        activity = (MainActivity) getActivity();

        editText = (EditText) rootView.findViewById(R.id.editText);
        searchButton = (Button) rootView.findViewById(R.id.search);
        galleryButton = (Button) rootView.findViewById(R.id.gallery);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "You should enter a type the query", Toast.LENGTH_SHORT).show();
                } else {
                    typeQuery = editText.getText().toString();

                    t1 = new SearchThread();
                    t2 = new SetImageThread();
                    Thread thread1 = new Thread(t1, typeQuery);
                    thread1.start();
                    try{
                        thread1.join();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    Thread thread2 = new Thread(t2, t1.imageUrl);
                    thread2.start();

                    try{
                        thread2.join();
                        bitmap = t2.bitmap;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    activity.searchImage(bitmap);

                    //Test code
                    System.out.println(t1.imageUrl);
                }


            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, 200);
            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),selectedImageUri);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            activity.searchImage(bitmap);
        }
    }
}
