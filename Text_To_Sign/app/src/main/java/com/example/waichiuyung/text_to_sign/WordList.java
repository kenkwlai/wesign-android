package com.example.waichiuyung.text_to_sign;

import java.util.List;

/**
 * Created by waichiuyung on 19/1/2017.
 */

public class WordList {

    private String word ;
    private String path;
    private String prefix;
    private int frequency;
    private String wordType;

    public WordList(String word,String path,String prefix, int frequency, String wordType) {
        this.word = word;
        this.path = path;
        this.prefix = prefix;
        this.frequency = frequency;
        this.wordType = wordType;

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
    public int getFrequency(){
        return frequency;
    }
    public void setFrequency(int frequency){
        this.frequency = frequency;
    }
    public String getWordType(){
        return wordType;
    }
    public void setWordType(String wordType){
        this.wordType = wordType;
    }




}
