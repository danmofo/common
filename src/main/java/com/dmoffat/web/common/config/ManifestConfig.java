package com.dmoffat.web.common.config;

import com.dmoffat.web.common.view.manifest.ManifestFileLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Manifest configuration for production and dev profiles.
 *
 * @author danielmoffat
 */
@Configuration
public class ManifestConfig {

    @Bean
    public ManifestFileLoader devManifestFileLoader(@Value(value = "classpath:manifest.json") Resource manifestFile,
                                                    ObjectMapper objectMapper) {

        return new ManifestFileLoader(manifestFile, objectMapper, true);
    }

}