package com.vickmwas;


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Socket TCPServer Implementation
 * Created by victor on 06/05/17.
 */

public class TCPServer {

    public static void main(String[] args) {

        int response;
        String matrixString;


        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(1234);

            Socket clientSocket = serverSocket.accept();
            Scanner sc = new Scanner(clientSocket.getInputStream());

            matrixString = sc.nextLine();
            System.out.println("Matrix String = " + matrixString);


            response = determinant(stringToMatrix(matrixString));
            System.out.println("Calculated determinant = " + response);
            System.out.println("\n==== Sending back " + response + " to server ...");

            PrintStream p = new PrintStream(clientSocket.getOutputStream());
            p.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] stringToMatrix(String stringMatrix){
        String[] array = stringMatrix.split(" ");
        int elements = array.length;
        int n = (int) Math.sqrt(elements);

        int matrix[][] = new int[n][n];

        int currentIndex = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                matrix[i][j] = Integer.parseInt(array[currentIndex]);
                currentIndex++;
            }
        }
        return matrix;
    }

    private static int determinant(int[][] matrix){
        int sum = 0;
        int s;
        if(matrix.length==1){  //bottom case of recursion. size 1 matrix determinant is itself.
            return(matrix[0][0]);
        }
        for(int i = 0; i < matrix.length; i++){ //finds determinant using row-by-row expansion
            int[][]smaller= new int[matrix.length-1][matrix.length-1]; //creates smaller matrix- values not in same row, column

            for(int a = 1; a < matrix.length; a++){
                for(int b = 0; b < matrix.length; b++){
                    if(b < i){
                        smaller[a-1][b]=matrix[a][b];
                    }
                    else if(b>i){
                        smaller[a-1][b-1]=matrix[a][b];
                    }
                }
            }
            if(i % 2 == 0){ //sign changes based on i
                s=1;
            } else{
                s=-1;
            }

            sum += s*matrix[0][i]*(determinant(smaller)); //recursive step: determinant of larger determined by smaller.
        }
        return(sum); //returns determinant value. once stack is finished, returns final determinant.
    }



}
