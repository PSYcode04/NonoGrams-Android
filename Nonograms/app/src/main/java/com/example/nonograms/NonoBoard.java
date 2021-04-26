package com.example.nonograms;

// 흑백으로 변환해서 0, 1로 이미지를 바꾼 이차원 배열을 입력받으면
// 출력해야할 보드판으로 생성해주는 클래스
public class NonoBoard {
    private int[][] grayscale;
    private int colMax;
    private int rowMax;
    private int[][] finalBoard; // 보드판 완성했을 때 담을 공간
    private int entireNum;


    public NonoBoard(int[][] grayscale) {
        this.grayscale = grayscale;
        setColMax();
        setRowMax();
        setBoard();
    }

    public int[][] getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(int[][] grayscale) {
        this.grayscale = grayscale;
    }

    public int getColMax() {
        return colMax;
    }

    public void setColMax(int colMax) {
        this.colMax = colMax;
    }

    public int getRowMax() {
        return rowMax;
    }

    public void setRowMax(int rowMax) {
        this.rowMax = rowMax;
    }

    public int[][] getFinalBoard() {
        return finalBoard;
    }

    public void setFinalBoard(int[][] finalBoard) {
        this.finalBoard = finalBoard;
    }

    public int getEntireNum() {
        return entireNum;
    }

    public void setEntireNum(int entireNum) {
        this.entireNum = entireNum;
    }

    private void setColMax(){
        int count = 0;
        int flag = 0;
        int max = 0;

        // 열을 기준으로 행을 증가시킨다. (i : 열)
        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (grayscale[j][i] == 0 && flag == 0) {
                    flag = 1;
                    count++;
                } else if (grayscale[j][i] == -1 && flag == 1) {
                    flag = 0;
                }
            }
            max = Math.max(max, count);
            count = 0;
            flag = 0;
        }

        colMax = max;
    }

    private void setRowMax(){
        int count = 0;
        int flag = 0;
        int max = 0;

        // 행을 기준으로 열을 증가시킨다. (i : 행)
        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (grayscale[i][j] == 0 && flag == 0) {
                    flag = 1;
                    count++;
                } else if (grayscale[i][j] == -1 && flag == 1) {
                    flag = 0;
                }
            }
            max = Math.max(max, count);
            flag = 0;
            count = 0;
        }

        rowMax = max;
    }

    private void setBoard(){
        finalBoard = new int[20+colMax][20+rowMax]; // 숫자부분만큼 추가해서 이차원 배열 초기화
        int colored = 0;    // 1(색칠된 부분) 개수 세기
        int flag = 0;
        int row = 0, col = 0;

        // 그냥 전체 -1로 초기화
        for(int i = 0; i < 20+colMax; i++)
            for(int j = 0; j < 20+rowMax; j++)
                finalBoard[i][j] = -2;


        // 보드판 부분 초기화 [colMax][rowMax] 부터
        for(int i = 0; i <20; i++)
            for(int j = 0; j < 20; j++)
                finalBoard[colMax+i][rowMax+j] = grayscale[i][j];



        // 보드판 [0~colMax-1][rowMax~rowMax+20] 까지는 위에 숫자 부분 (i : 열)
        for(int i = 0; i < 20; i++) {
            flag = 0;
            row = colMax - 1;   // 열 최대 개수 - 1 까지가 위에 숫자 부분
            colored = 0;
            for(int j = 19; j >= 0; j--) {
                if (grayscale[j][i] == 0) {
                    flag = 1;
                    colored++;
                } else if(grayscale[j][i] == -1 && flag == 1) {
                    flag = 0;
                    finalBoard[row][rowMax+i] = colored;
                    entireNum += colored;
                    row--;
                    colored = 0;
                }

                if(flag == 1 && j == 0) {
                    finalBoard[row][rowMax+i] = colored;
                    entireNum += colored;
                }

            }
        }



        // 보드판 [colMax~colMax+20][0~rowMax-1] 까지는 왼쪽 숫자 부분 (i : 행)
        for(int i = 0; i < 20; i++) {
            flag = 0;
            col = rowMax - 1;
            colored = 0;
            for(int j = 19; j >= 0; j--) {
                if (grayscale[i][j] == 0) {
                    flag = 1;
                    colored++;
                } else if(grayscale[i][j] == -1 && flag == 1) {
                    flag = 0;
                    finalBoard[colMax+i][col] = colored;
                    col--;
                    colored = 0;
                }

                if(flag == 1 && j == 0) {
                    finalBoard[colMax+i][col] = colored;
                }


            }
        }

    }
}
