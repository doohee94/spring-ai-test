package com.example.iciinsight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        return factory;
    }

    @Bean
    public WebClient litSenseWebClient() {
        return WebClient.builder()
                .defaultHeader("User-Agent", "Mozilla/5.0")
                .baseUrl("https://www.ncbi.nlm.nih.gov/research/litsense-api/api/")
                .build();
    }

    @Bean
    public WebClient ncbiWebClient() {
        return WebClient.builder()
                .baseUrl("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi")
                .build();
    }

    @Bean
    public WebClient DGIdbWebClient() {
        return WebClient.builder()
                .baseUrl("https://dgidb.org/api/graphql")
                .build();
    }


}