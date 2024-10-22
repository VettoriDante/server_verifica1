package com.server_verifica;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        int porta = 3000;
        ServerSocket server = new ServerSocket(porta);
        System.out.println("Server avviato con successo sulla porta " + porta);
        
        ArrayList<Integer> classifica = new ArrayList<>(); 
        //numero random
        Random random = new Random();
        int numero = random.nextInt(100);
        ServerThread.find = numero;
        System.out.println("Numero da indovinare = " + numero);

        do {
            Socket skt = server.accept();
            System.out.println("un client si è collegato");

            ServerThread cl = new ServerThread(skt, classifica);
            cl.start();
        } while (true);
    }
}