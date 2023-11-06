package com.line4thon.kureomi;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GreeneyeApiClient {
    private final String invokeUrl; // Greeneye API invoke URL
    private final String secretKey; // Greeneye API secret key

    @Value("${greeneye.api.invokeUrl}")
    private String greeneyeInvokeUrl;

    @Value("${greeneye.api.secretKey}")
    private String greeneyeSecretKey;

    public GreeneyeApiClient() {
        this.invokeUrl = greeneyeInvokeUrl;
        this.secretKey = greeneyeSecretKey;
    }

    public GreeneyeApiResponse sendImageForAnalysis(String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);

        HttpEntity<String> request = new HttpEntity<>(imageUrl, headers);

        ResponseEntity<GreeneyeApiResponse> responseEntity = restTemplate.exchange(
                invokeUrl,
                HttpMethod.POST,
                request,
                GreeneyeApiResponse.class
        );

        return responseEntity.getBody();
    }
}

