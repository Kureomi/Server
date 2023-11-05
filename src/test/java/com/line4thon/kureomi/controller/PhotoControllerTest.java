package com.line4thon.kureomi.controller;

import com.line4thon.kureomi.domain.giftBox.GiftBoxRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Container
    private static final GenericContainer<?> imageMagickContainer = new GenericContainer<>("jodogne/imagemagick:latest")
            .withExposedPorts(80);

    @Autowired
    private GiftBoxRepository giftBoxRepository;

    @AfterEach
    public void tearDown() throws Exception {
        giftBoxRepository.deleteAll();
    }

    @Test
    public void 사진6장_저장() throws Exception {
        // Given
        String containerIpAddress = imageMagickContainer.getContainerIpAddress();
        Integer containerPort = imageMagickContainer.getMappedPort(80);

        for (int i = 0; i < 6; i++) {
            createAndSaveFakeImage(containerIpAddress, containerPort, i);
        }

        // When
        ResponseEntity<List<Long>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/api/v1/kureomi/photo",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<List<Long>>() {} // 반환 유형 명시
        );


        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Long> photoIdList = responseEntity.getBody();
        assertThat(photoIdList).isNotNull();
        assertThat(photoIdList.size()).isEqualTo(6);
    }

    private static void createAndSaveFakeImage(String containerIpAddress, Integer containerPort, int index) {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        try {
            File output = new File("fake_image_" + index + ".jpg");
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker",
                    "cp",
                    "fake_image_" + index + ".jpg",
                    containerIpAddress + ":" + containerPort + "/fake_image_" + index + ".jpg"
            );
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
