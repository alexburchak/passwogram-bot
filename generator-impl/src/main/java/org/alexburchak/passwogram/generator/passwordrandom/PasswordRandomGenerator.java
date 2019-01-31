package org.alexburchak.passwogram.generator.passwordrandom;

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
public class PasswordRandomGenerator implements Generator {
    static final String URI = "https://www.passwordrandom.com/query?command=password";

    private RestTemplate restTemplate;

    public PasswordRandomGenerator(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .build();
    }

    @Override
    public String getName() {
        return GeneratorNames.PASSWORD_RANDOM_GENERATOR;
    }

    @Override
    public String generate(@SuppressWarnings("unused") String sample) {
        log.debug("Asked for new password from passwordrandom.com");

        return restTemplate.getForObject(URI, String.class);
    }
}
