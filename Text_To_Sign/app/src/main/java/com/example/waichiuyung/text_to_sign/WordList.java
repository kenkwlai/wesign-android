package com.example.waichiuyung.text_to_sign;

/**
 * Created by waichiuyung on 19/1/2017.
 */

public class WordList {

    private String word ;
    private String path;
    private String prefix;
    private double duration;
    private String type1;
    private String type2;

    public WordList(String word, String path, String prefix, double duration, String type1, String type2) {
        this.word = word;
        this.path = path;
        this.prefix = prefix;
        this.duration = duration;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getWord(){
        return word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPrefix(){
        return prefix;
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    public double getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public String getType1(){
        return type1;
    }

    public void setType1(String type1){
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }
}
