package com.server_verifica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Classifiche{
    //classe interna per creare un livello e relativa classifica
    static class Classifica {
        private int searchFor;
        private ArrayList<Integer> classifica;

        public Classifica() {
            Random random = new Random();
            int numero = random.nextInt(100);
            this.searchFor = numero;

            classifica = new ArrayList<>();
        }

        synchronized void addScoreRank(int score){
            classifica.add(score);
            Collections.sort(classifica);
        }

        public int getSearchFor() {
            return searchFor;
        }

        public ArrayList<Integer> getClassifica() {
            return classifica;
        }

    }
    private ArrayList<Classifica> livelli;
    
    //costruttore
    public Classifiche() {
        this.livelli = new ArrayList<>();
        livelli.add(new Classifica());
    }

    public Classifica nextLevel(int levelIndex){
        if(levelIndex >= livelli.size()){
            Classifica nL = new Classifica();
            livelli.add(nL);
            return  nL;
        }

        return livelli.get(levelIndex);
    }

    public void addScore(int levelIndex, int score){
        livelli.get(levelIndex).addScoreRank(score);
    }

    public Classifica getLevel(int levelIndex){
        return livelli.get(levelIndex);
    }

    
}
