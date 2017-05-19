package com.vickmwas;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP UDPServer implementation
 * Created by victor on 09/05/17.
 */
public class UDPServer {

    public static void main(String args[]) throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData;
        System.out.println("Starting server...");
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String stringFromClient = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + stringFromClient);


            System.out.println("Extracting matrix from string:");
            int[][] matrix = stringToMatrix(stringFromClient);
            printMatrix(matrix);


            String determinantString = String.valueOf(determinant(matrix));
            System.out.println("\nCalculated Determinant = " + determinantString);


            sendData = determinantString.getBytes();

            //send determinant back to client
            DatagramPacket sendPacket =
                    new DatagramPacket(
                            sendData,
                            sendData.length,
                            receivePacket.getAddress(),
                            receivePacket.getPort());

            serverSocket.send(sendPacket);
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



    private static void printMatrix(int MATRIX[][]){
        for (int[] row : MATRIX) {
            for (int j = 0; j < MATRIX.length; j++) {
                System.out.print(row[j] + "  ");
            }
            System.out.println("");
        }
    }

}
