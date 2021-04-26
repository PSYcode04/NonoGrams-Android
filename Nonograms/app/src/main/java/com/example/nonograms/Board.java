package com.example.nonograms;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Board extends Fragment {
    MainActivity activity;
    Bitmap bitmapImage;
    Bitmap newBitmap;
    Bitmap[] bitmapPiece;
    NonoBoard newBoard; //  게임 위한 보드
    int answerNum;

    boolean finish;

    int[] grayScale;    // gray scale 이 흰색이면 0, 검은색이면 1 저장
    int[][] grayScale2;
    int[][] testBoard;
    int check;

    int[] testArray;
    int[] answer;

    GridView gridView;
    BoardViewAdapter adapter;


    Thread t1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.board, container, false);
        activity = (MainActivity) getActivity();
        grayScale2 = new int[20][20];
        check = 0;
//        finish = false;

        gridView = (GridView) rootView.findViewById(R.id.gridView);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(answer[position] == 0) {
                    // 검은 부분 클릭
                    answer[position] = -3;
                    // 그리드뷰 업데이트
                    adapter = new BoardViewAdapter(getContext(), answer, newBoard.getRowMax()+20, newBoard.getColMax()+20);
                    gridView.setAdapter(adapter);
                    gridView.setNumColumns(newBoard.getRowMax()+20);
                    answerNum--;

                    if(answerNum == 0) {
                        Toast.makeText(getActivity(), "FINISH!", Toast.LENGTH_LONG).show();
                    }

                } else if(answer[position] == -1) {
                    //  흰버튼 클릭
                    Toast.makeText(getActivity(), "WRONG!", Toast.LENGTH_SHORT).show();
                    // 뷰 초기화
                    answer = testArray.clone();
                    adapter = new BoardViewAdapter(getContext(), testArray, newBoard.getRowMax()+20, newBoard.getColMax()+20);
                    gridView.setAdapter(adapter);
                    gridView.setNumColumns(newBoard.getRowMax()+20);
                    answerNum = newBoard.getEntireNum();
                }

            }

        });

        return rootView;
    }

    public void setImage(Bitmap bitmap){
        bitmapImage = bitmap;
        finish = false;

        newBitmap = Bitmap.createScaledBitmap(bitmapImage, 400, 400, true);


        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                newBitmap = grayScale(newBitmap);
                splitImage(newBitmap);
            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        for(int i = 0; i < 20; i ++){
            for(int j = 0; j < 20; j++) {
                grayScale2[i][j] = grayScale[check++];
            }
        }
        check = 0;
        // 여기까지 흑백 완성


        // 보드판 생성
        newBoard = new NonoBoard(grayScale2);
        answerNum = newBoard.getEntireNum();



        testBoard = new int[20+newBoard.getColMax()][20+newBoard.getRowMax()];
        testBoard = new int[20+newBoard.getColMax()][20+newBoard.getRowMax()];
        testBoard = newBoard.getFinalBoard();


        testArray = twoDToOne(testBoard);
        answer = testArray.clone();



        // 그리드뷰 어댑터 생성
        adapter = new BoardViewAdapter(getContext(), testArray, newBoard.getRowMax()+20, newBoard.getColMax()+20);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(newBoard.getRowMax()+20);
    }


    public Bitmap grayScale(Bitmap orgBitmap) {
        int width, height;
        width = orgBitmap.getWidth();
        height = orgBitmap.getHeight();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = orgBitmap.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                // use 128 as threshold, above -> white, below -> black
                if (gray > 128)
                    gray = 255;
                else
                    gray = 0;
                // set new pixel color to output bitmap
                bmpGrayScale.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmpGrayScale;
    }

    public int avgGray(Bitmap orgBitmap){
        int width, height;
        width = orgBitmap.getWidth();
        height = orgBitmap.getHeight();
        int white = 0, black = 0;

        // color information
        int A, R, G, B;
        int pixel;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = orgBitmap.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                if (gray > 128)
                    white += 1;
                else
                    black += 1;
            }
        }

        if(white > black)
            return -1;
        else
            return 0;
    }

    public void splitImage(Bitmap grayImage) {
        int x, y;
        int i = 0;
        bitmapPiece = new Bitmap[400];
        grayScale = new int[400];

        for (y = 0; y <= (grayImage.getHeight() - grayImage.getHeight() / 20); y += grayImage.getHeight() / 20) {
            for (x = 0; x <= (grayImage.getWidth() - grayImage.getWidth() / 20); x += grayImage.getWidth() / 20, i++) {
                if(i == 400)
                    break;
                bitmapPiece[i] = Bitmap.createBitmap(grayImage, x, y, grayImage.getWidth()/ 20, grayImage.getHeight() / 20);
                grayScale[i] = avgGray(bitmapPiece[i]);
            }
        }
    }

    public int[] twoDToOne(int[][] board) {
        int arr[] = new int[board.length*board[0].length];

        for(int i = 0; i <board.length; i++)
            for(int j = 0; j < board[0].length; j++) {
                arr[i*board[0].length+j] = board[i][j];
            }
        return arr;
    }
}
