package com.odde.atddv2.controller;

import com.odde.atddv2.repo.SchoolRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class ScoreController {

    @Autowired
    private SchoolRepo schoolRepo;

    @SneakyThrows
    @GetMapping("/score")
    public HttpEntity<byte[]> score() {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentDispositionFormData("attachment", encode("score.zip", UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            schoolRepo.findAll().forEach(school -> school.archive(zipOutputStream));
        }
        return new HttpEntity<>(outputStream.toByteArray(), respHeaders);
    }
}
