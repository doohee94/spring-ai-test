package com.example.iciinsight.service;

import com.example.iciinsight.dto.LitSenseDto;
import com.example.iciinsight.dto.LitSenseList;
import lombok.RequiredArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LitSenseService {

    private final WebClient litSenseWebClient;

    public LitSenseList getLitsenseList(String gene, String drug) {

        Mono<List<LitSenseDto>> litSenseDtoMono = litSenseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format", "json")
                        .queryParam("query", "Are there any links between "+drug+" and " + gene)
                        .queryParam("rerank", "true")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LitSenseDto>>() {
                });


        return new LitSenseList(litSenseDtoMono.block()) ;
    }
}
