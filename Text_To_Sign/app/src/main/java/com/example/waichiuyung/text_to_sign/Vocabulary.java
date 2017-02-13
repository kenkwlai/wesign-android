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
    @JsonProperty("wordType")
    private String wordType;
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

    public String getWordType() {
        return wordType;
    }
}
