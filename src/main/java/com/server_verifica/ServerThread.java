package com.server_verifica;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    static int find;

    public ServerThread(Socket socket) {
        this.socket = socket;
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
                if(outDatas.equals("=\n")) out.writeBytes(score +"\n"); 
    
            }while(goOn);
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
    
}
