package com.server_verifica;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.server_verifica.Classifiche.Classifica;

public class ServerThread extends Thread{
    private Socket socket;
    private Classifiche livelli;
    private int levelIndex;

    public ServerThread(Socket socket, Classifiche livelli) {
        this.socket = socket;
        this.livelli = livelli;
        this.levelIndex = 0;
    }

    @Override
    public void run() {
        try {
            int score = 0;
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean goOn = true;
            int find = 0;
            
            do{
                if(score == 0){
                    System.out.println("Livello " + levelIndex +"\nNumero = " + livelli.getLevel(levelIndex).getSearchFor());
                    find = livelli.getLevel(levelIndex).getSearchFor();

                }
                
                score++;
                String input = in.readLine();
                String outDatas = checkInput(input, find);

                out.writeBytes(outDatas);
                if(outDatas.equals("=\n")){
                    //scrivo il puteggio
                    out.writeBytes(score +"\n");
                    scoreAdd(score, levelIndex);
                    livelli.getLevel(levelIndex);
                    //scrivo la classifica
                    out.writeBytes(livelli.getLevel(levelIndex).getClassifica().toString() +"\n");
                    score = 0 ;
                    goOn = false;
                    String again = in.readLine();
                    if(again.equalsIgnoreCase("y")){
                        goOn = true;
                        nextLevel();
                        out.writeBytes("y\n");
                    }else
                    {
                        out.writeBytes("n\n");
                    }

                }
            }while(goOn);
        socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkInput(String input,int find){
        int val = 0;
        
        try{
            val = Integer.parseInt(input);
        }
        catch(Exception e)
        {
             return "NN\n";
        }
        if(val < 0 || val > 100) return "<>\n";
        if(val < find) return "<\n";
        if(val > find) return ">\n";
        if(val == find) return "=\n";//se indovina
        return "!";
    }

    synchronized private void scoreAdd(int score,int levelIndex){
        livelli.addScore(levelIndex, score);
    }
    
    private void nextLevel(){
        levelIndex++;
        livelli.nextLevel(levelIndex);
    }
}
