package com.lixl.study.DataStructureAndAlgorithm.zuochengyong讲课.矩阵;

/**
 * @author lixinlin
 * @date 2023/2/22
 */
public class 蛇形打印矩阵 {
    public static void printMatrixZigZag(int[][] matrix) {
        int Arow = 0;
        int Acolumn = 0;
        int Brow = 0;
        int Bcolumn = 0;
        int endRow = matrix.length - 1;
        int endColumn = matrix[0].length - 1;

        boolean formup = false;

        while (!(Acolumn == Bcolumn && Arow == Brow)) {//没有汇合
            printShexing(matrix, Arow, Acolumn, Brow, Bcolumn, formup);
            if (Acolumn == endColumn) {//已经到了最后一列，需要换行
                Arow = Arow + 1;//换行
            } else {
                Acolumn = Acolumn + 1;//不换行，只前进一列
            }
            if (Brow == endRow) {
                Bcolumn = Bcolumn + 1;
            } else {
                Brow = Brow + 1;
            }
            formup = !formup;
        }
    }

    private static void printShexing(int[][] matrix, int arow, int acolumn, int brow, int bcolumn, boolean formup) {
        if (formup) {//从A到B
            while (acolumn != bcolumn) {
                int matrix1 = matrix[acolumn][arow];
                System.out.println(matrix1);
                acolumn--;
                arow++;
            }
        } else {//从B到A
            while (bcolumn != acolumn) {
                int matrix1 = matrix[bcolumn][brow];
                System.out.println(matrix1);
                bcolumn++;
                brow--;
            }
        }
    }

    //forTest
    public static void main(String[] args) {
        int[][] matrix = new int[3][4];
        printMatrixZigZag(matrix);
    }

}
