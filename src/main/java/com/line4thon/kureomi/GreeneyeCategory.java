package com.line4thon.kureomi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreeneyeCategory {
    @JsonProperty("confidence")
    private double confidence;

    // Getter and Setter methods for confidence

    public double getConfidence() {
        return confidence;
    }
}
