package com.example.myapplication;

public class Info_item {
    String contents;
    String result;

    public Info_item(String contents, String result){
        this.contents=contents;
        this.result=result;


}
    public Info_item(){}

    public String getContents(){
        return contents;
    }

    public void setNum(String contents){
        this.contents= contents;
    }

    public String getResult(){ return result;}

    public void setResult(String result){this.result=result;}

}
//슬기
