package com.server_verifica;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class ServerThread extends Thread{
    static int find;
    private Socket socket;
    private ArrayList<Integer> classifica;

    public ServerThread(Socket socket, ArrayList<Integer> classifica) {
        this.socket = socket;
        this.classifica = classifica;
    }

    @Override
    public void run() {
        try {
            int score = 0;
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean goOn = true;

            do{
                score++;
                String input = in.readLine();
                String outDatas = checkInput(input);

                out.writeBytes(outDatas);
                if(outDatas.equals("=\n")){
                    out.writeBytes(score +"\n");
                    scoreAdd(score);
                    Collections.sort(classifica);
                    out.writeBytes(classifica.toString());
                    goOn = false;
                }
            }while(goOn);
        socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkInput(String input){
        int val = 0;
        
        try{
            val = Integer.parseInt(input);
        }
        catch(Exception e)
        {
             return "NN\n";
        }
        if(val < 0 || val > 100) return "<>\n";
        if(val < ServerThread.find) return "<\n";
        if(val > ServerThread.find) return ">\n";
        if(val == ServerThread.find) return "=\n";//se indovina
        return "!";
    }

    synchronized private void scoreAdd(int score){
        classifica.add(score);
    }
    
}
