package com.wut.ersms.mainservice.stocks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class StockServiceClientConfig {

    @Value("${stock.service.address}")
    private String baseUrl;

    @Bean("stockServiceClient")
    public RestClient stockServiceClient() {
        return RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
//                .messageConverters(converters -> converters.add(new StringHttpMessageConverter()))
                .baseUrl(baseUrl)
//                .defaultUriVariables(Map.of("variable", "foo"))
//                .defaultHeader("My-Header", "Foo")
//                .defaultCookie("My-Cookie", "Bar")
                .build();
    }

}
