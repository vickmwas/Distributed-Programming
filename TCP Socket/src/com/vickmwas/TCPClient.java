package com.vickmwas;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private static int MATRIX[][];
    private static int n;

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("127.0.0.1", 1234);

            Scanner sc1 = new Scanner(socket.getInputStream());


            getMatrixFromUser();


            System.out.println("\n Here is your matrix : ");
            printMatrix();


            String matrixString = matrixToString();

            PrintStream p = new PrintStream(socket.getOutputStream());
            p.println(matrixString);

            int serverOutput = sc1.nextInt();

            System.out.println(" The Matrix Determinant calculated by the server: " + serverOutput);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void getMatrixFromUser(){
        Scanner sc = new Scanner(System.in);

        System.out.println("===============================================================\n");
        System.out.println("Welcome to the TCP/IP Matrix Determinant Calculator");
        System.out.println("===============================================================\n");

        System.out.println("Enter the size n of the (n x n) matrix");

        n = sc.nextInt();
        while (n < 3){
            System.out.println("N must be > 3. Try again : ");
            n = sc.nextInt();
        }
        MATRIX = new int[n][n];

        System.out.println("Success! \n Now let's enter the elements : ");
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){

                System.out.print("matrix[" + i + "][" + j+"]  :  ");

                while (!sc.hasNextInt()){
                    System.out.println("Invalid input. Retry ");
                    System.out.print("matrix[" + i + "][" + j + "]  :  ");
                    sc.next();
                }
                MATRIX[i][j] = sc.nextInt();
            }
        }


    }

    private static void printMatrix(){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.print(MATRIX[i][j] + "  ");
            }
            System.out.println("");
        }
    }


    private static String matrixToString(){
        String matrixString = "";
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                matrixString = matrixString.concat(MATRIX[i][j] + " ");
            }

        }
        return matrixString;
    }


}
