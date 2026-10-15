package com.system_design.url_shortener.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.view.RedirectView;
import java.util.NoSuchElementException;

import com.system_design.url_shortener.model.UrlMapping;
import com.system_design.url_shortener.service.UrlShortenerService;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/api/urls")
    public ResponseEntity<?> createShortUrl(
            @RequestBody Map<String, String> request) {

        String longUrl = request.get("longUrl");

        if (longUrl == null || longUrl.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "longUrl is required"));
        }

        UrlMapping mapping = service.createShortUrl(longUrl);

        String shortUrl = "http://localhost:8080/"
                + mapping.getShortCode();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "shortCode", mapping.getShortCode(),
                        "shortUrl", shortUrl
                ));
    }
    


	@GetMapping("/{shortCode}")
	public ResponseEntity<?> redirect(@PathVariable String shortCode) {
	    try {
	        UrlMapping mapping = service.getOriginalUrl(shortCode);
	
	        return ResponseEntity.status(HttpStatus.FOUND)
	                .location(java.net.URI.create(mapping.getOriginalUrl()))
	                .build();
	
	    } catch (NoSuchElementException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Short URL not found");
	    }
	}


}