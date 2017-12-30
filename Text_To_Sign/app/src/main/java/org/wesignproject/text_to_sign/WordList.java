package org.wesignproject.text_to_sign;

/**
 * Created by waichiuyung on 19/1/2017.
 */

public class WordList {

    private String word ;
    private String path;
    private String prefix;
    private double duration;
    private String mainType;
    private String subType;

    public WordList(String word, String path, String prefix, double duration, String mainType, String subType) {
        this.word = word;
        this.path = path;
        this.prefix = prefix;
        this.duration = duration;
        this.mainType = mainType;
        this.subType = subType;
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

    public String getMainType(){
        return mainType;
    }

    public void setMainType(String mainType){
        this.mainType = mainType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
}
