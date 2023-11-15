package com.line4thon.kureomi.service;

import com.line4thon.kureomi.web.dto.GreeneyeRequestDto;
import com.line4thon.kureomi.web.dto.PhotoTestRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Slf4j
@Service
public class GreenEyeService {

    @Value("${invokeUrl}")
    private String invokeUrl;

    @Value("${secretKey}")
    private String secretKey;

    public boolean testPhoto(MultipartFile file, String data) {
        try {
            GreeneyeRequestDto testRequestDto = GreeneyeRequestDto.builder()
                    .requestId("requestId")
                    .timestamp(System.currentTimeMillis())
                    .images(Collections.singletonList(new PhotoTestRequestDto(file.getOriginalFilename(), data)))
                    .build();

            log.info("요청 body: {}", testRequestDto.toString());

            String apiResponse = callGreeneyeApi(testRequestDto);

            if (isNormalPhoto(apiResponse) || isAdultPhoto(apiResponse)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String callGreeneyeApi(GreeneyeRequestDto testRequestDto) {
        WebClient webClient = WebClient.builder()
                .baseUrl(invokeUrl)
                .defaultHeader("X-GREEN-EYE-SECRET", secretKey)
                .defaultHeader("Content-Type", "application/json")
                .build();

        String jsonResponse = webClient.post()
                .body(BodyInserters.fromValue(testRequestDto))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("응답: {}", response))
                .block();

        return jsonResponse;
    }

    private boolean isNormalPhoto(String apiResponse) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(apiResponse);
            JSONArray imageArray = (JSONArray) response.get("images");
            log.info("{}", imageArray);

            if (imageArray != null && !imageArray.isEmpty()) {
                JSONObject firstImage = (JSONObject) imageArray.get(0);

                Double imageConfidence = (Double) firstImage.get("confidence");

                JSONObject resultObject = (JSONObject) firstImage.get("result");
                JSONObject normalObject = (JSONObject) resultObject.get("normal");
                Double normalConfidence = (Double) normalObject.get("confidence");

                if (normalConfidence != null) {
                    return imageConfidence.equals(normalConfidence);
                }
                else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAdultPhoto(String apiResponse) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject response = (JSONObject) parser.parse(apiResponse);
            JSONArray imageArray = (JSONArray) response.get("images");
            log.info("{}", imageArray);

            if (imageArray != null && !imageArray.isEmpty()) {
                JSONObject firstImage = (JSONObject) imageArray.get(0);

                Double imageConfidence = (Double) firstImage.get("confidence");

                JSONObject resultObject = (JSONObject) firstImage.get("result");
                JSONObject adultObject = (JSONObject) resultObject.get("adult");
                Double adultConfidence = (Double) adultObject.get("confidence");

                if (adultConfidence != null) {
                    return imageConfidence.equals(adultConfidence);
                }
                else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
