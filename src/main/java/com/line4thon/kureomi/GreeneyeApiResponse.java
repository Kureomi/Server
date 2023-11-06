package com.line4thon.kureomi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GreeneyeApiResponse {
    @JsonProperty("version")
    private String version;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("images")
    private List<GreeneyeImage> images;

    // Getter and Setter methods for all fields

    public List<GreeneyeImage> getImages() {
        return images;
    }
}

