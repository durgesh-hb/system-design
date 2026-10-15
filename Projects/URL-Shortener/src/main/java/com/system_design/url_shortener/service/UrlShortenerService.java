
package com.system_design.url_shortener.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.system_design.url_shortener.model.UrlMapping;
import com.system_design.url_shortener.repository.UrlMappingRepository;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository repository;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    public UrlMapping createShortUrl(String originalUrl) {

        String shortCode = java.util.UUID.randomUUID()
                .toString()
                .substring(0, 8);

        UrlMapping mapping = new UrlMapping(
                shortCode,
                originalUrl,
                LocalDateTime.now(),
                null
        );

        return repository.save(mapping);
    }
    

	public UrlMapping getOriginalUrl(String shortCode) {
	    return repository.findById(shortCode)
	            .orElseThrow(() ->
	                    new java.util.NoSuchElementException(
	                            "Short URL not found"));
	}

}
