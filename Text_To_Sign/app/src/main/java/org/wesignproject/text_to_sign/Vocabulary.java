package org.wesignproject.text_to_sign;

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
    @JsonProperty("mainType")
    private String mainType;
    @JsonProperty("subType")
    private String subType;
    @JsonProperty("prefix")
    private String prefix;
    @JsonProperty("duration")
    private Double duration;

    public String getWord() {
        return word;
    }

    public Double getDuration() {
        return duration;
    }

    public String getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getMainType() {
        return mainType;
    }

    public String getSubType() {
        return subType;
    }
}
