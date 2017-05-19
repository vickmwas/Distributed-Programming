package com.vickmwas;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    private static int MATRIX[][];
    private static int n;

    public static void main(String[] args) {
        byte[] sendData;
        byte[] receiveData = new byte[1024];

        getMatrixFromUser();

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            String sentence = matrixToString();

            sendData = sentence.getBytes();

            //datagram packet
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, IPAddress, 9876);

            clientSocket.send(sendPacket);


            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData());

            System.out.println("FROM SERVER:" + modifiedSentence);
            clientSocket.close();

        } catch ( IOException e) {
            e.printStackTrace();
        }

    }



    private static void getMatrixFromUser(){
        Scanner sc = new Scanner(System.in);

        System.out.println("===============================================================\n");
        System.out.println("Welcome to the Matrix Determinant Calculator\n\nImplemented using UDP Protocol\n");
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

        System.out.println("\nHere is your matrix :");
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

