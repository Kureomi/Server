package com.line4thon.kureomi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreeneyeResult {
    @JsonProperty("adult")
    private GreeneyeCategory adult;

    @JsonProperty("normal")
    private GreeneyeCategory normal;

    @JsonProperty("porn")
    private GreeneyeCategory porn;

    @JsonProperty("sexy")
    private GreeneyeCategory sexy;

    // Getter methods for all fields

    public GreeneyeCategory getAdult() {
        return adult;
    }

    public GreeneyeCategory getNormal() {
        return normal;
    }

    public GreeneyeCategory getPorn() {
        return porn;
    }

    public GreeneyeCategory getSexy() {
        return sexy;
    }
}
