package com.example.nonograms;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoardViewAdapter extends BaseAdapter {
    private Context c;
    private int boardRow;
    private int boardCol;
    private int addCol; // 추가된 열 부분
    private int addRow; // 추가된 행 부분
    private int[] boardArray;

    public BoardViewAdapter(Context context, int[] arr, int row, int col) {
        c = context;
        boardRow = row;
        boardCol = col;
        addCol = col-20;
        addRow = row - 20;

        boardArray = arr;
    }



    @Override
    public int getCount() {
        return boardRow * boardCol;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;


        if(convertView == null) {
            textView = new TextView(c);
            textView.setPadding(0,0,0,0);
            textView.setSingleLine();
            textView.setTextSize(10);

        } else {
            textView = (TextView) convertView;
        }



        if(boardArray[position] == -2){
            textView.setText(" ");
            return textView;
        }

        // 보드 부분
        if(boardArray[position] == 0 || boardArray[position] == -1){
            textView.setText(" ");
            textView.setBackgroundColor(Color.WHITE);
            return textView;
        }

        // 정답 클릭한 부분
        if(boardArray[position] == -3) {
            textView.setText(" ");
            textView.setBackgroundColor(Color.BLACK);
            return textView;
        }


        textView.setText(String.valueOf(boardArray[position]));
        textView.setSingleLine();

        return textView;
    }
}
