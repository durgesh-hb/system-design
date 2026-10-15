
package com.system_design.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system_design.url_shortener.model.UrlMapping;

public interface UrlMappingRepository
        extends JpaRepository<UrlMapping, String> {
}
