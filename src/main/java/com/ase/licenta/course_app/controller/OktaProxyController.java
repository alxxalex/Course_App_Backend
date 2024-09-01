package com.ase.licenta.course_app.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/okta")
public class OktaProxyController {

    private String oktaApiToken = "00iWkeWutatdjuKex4LkanrUXR5X6Mdv92NY7QHOEs";

    private final String oktaGroupUrl = "https://dev-92161378.okta.com/api/v1/groups/00ggl63xbvgufTMrM5d7/users";

    private final RestTemplate restTemplate;

    public OktaProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/users")
    public ResponseEntity<String> getOktaGroupUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SSWS " + oktaApiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    oktaGroupUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return ResponseEntity.ok().body(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
