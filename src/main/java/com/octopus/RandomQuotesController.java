package com.octopus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomQuotesController {
    @RequestMapping("/api/quote")
    public String index() {
        return "{\"quote\": \"test\", " +
                "\"author\": \"test\", " +
                "\"appVersion\": \"0.0.1\", " +
                "\"environmentName\": \"Dev\" " +
                "}";
    }
}
