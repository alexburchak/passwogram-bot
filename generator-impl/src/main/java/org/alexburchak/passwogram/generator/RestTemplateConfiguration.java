package org.alexburchak.passwogram.generator;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.net.URI;

/**
 * @author alexburchak
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(60000);
        clientHttpRequestFactory.setReadTimeout(60000);

        return new RestTemplateBuilder()
                .requestFactory(() -> clientHttpRequestFactory)
                .errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(@SuppressWarnings("unused") URI url, @SuppressWarnings("unused") HttpMethod method, @SuppressWarnings("unused") ClientHttpResponse response) {
                    }
                });
    }
}
