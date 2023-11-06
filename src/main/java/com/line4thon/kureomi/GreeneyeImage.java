package com.line4thon.kureomi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreeneyeImage {
    @JsonProperty("message")
    private String message;

    @JsonProperty("name")
    private String name;

    @JsonProperty("result")
    private GreeneyeResult result;

    @JsonProperty("latency")
    private double latency;

    @JsonProperty("confidence")
    private double confidence;

    // Getter and Setter methods for all fields

    public GreeneyeResult getResult() {
        return result;
    }
}
