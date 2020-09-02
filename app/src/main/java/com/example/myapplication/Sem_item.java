package com.example.myapplication;

public class Sem_item {
    String name;
    String num;

    public Sem_item(String num, String name){
        this.num=num;
        this.name=name;
    }

    public Sem_item(){}

    public String getNum(){
        return num;
    }

    public void setNum(String num){
        this.num= num;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name =name;
    }
}

//슬기