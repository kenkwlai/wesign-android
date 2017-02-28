package com.example.waichiuyung.text_to_sign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by waichiuyung on 17/1/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vocabulary implements Serializable {
    @JsonProperty("word")
    private String word;
    @JsonProperty("path")
    private String path;
    @JsonProperty("type1")
    private String type1;
    @JsonProperty("type2")
    private String type2;
    @JsonProperty("prefix")
    private String prefix;
    @JsonProperty("frequency")
    private Long frequency;

    public String getWord() {
        return word;
    }

    public Long getFrequency() {
        return frequency;
    }

    public String getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }
}
