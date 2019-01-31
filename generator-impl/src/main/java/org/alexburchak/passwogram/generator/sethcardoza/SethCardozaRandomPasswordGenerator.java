package org.alexburchak.passwogram.generator.sethcardoza;

import lombok.extern.slf4j.Slf4j;
import org.alexburchak.passwogram.generator.GeneratorNames;
import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author alexburchak
 */
@Component
@Slf4j
public class SethCardozaRandomPasswordGenerator implements Generator {
    static final String URI = "http://www.sethcardoza.com/api/rest/tools/random_password_generator/length:16/complexity:alphaNumSpecial";

    private RestTemplate restTemplate;

    public SethCardozaRandomPasswordGenerator(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .build();
    }

    @Override
    public String getName() {
        return GeneratorNames.SETH_CARDOZA_RANDOM_PASSWORD;
    }

    @Override
    public String generate(@SuppressWarnings("unused") String sample) {
        log.debug("Asked for new password from http://www.sethcardoza.com");

        return restTemplate.getForObject(URI, String.class);
    }
}
