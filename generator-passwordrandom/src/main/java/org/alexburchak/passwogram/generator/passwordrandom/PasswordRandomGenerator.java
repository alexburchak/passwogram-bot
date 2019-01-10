package org.alexburchak.passwogram.generator.passwordrandom;

import lombok.extern.slf4j.Slf4j;
import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author alexburchak
 */
@Component
@Slf4j
public class PasswordRandomGenerator implements Generator {
    @Override
    public String generate(@SuppressWarnings("unused") String sample) {
        log.debug("Asked for new password from passwordrandom.com");

        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(60000);
        clientHttpRequestFactory.setReadTimeout(60000);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .requestFactory(() -> clientHttpRequestFactory)
                .errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(@SuppressWarnings("unused") URI url, @SuppressWarnings("unused") HttpMethod method, @SuppressWarnings("unused") ClientHttpResponse response) {
                    }
                })
                .build();

        return restTemplate.getForObject("https://www.passwordrandom.com/query?command=password", String.class);
    }
}
