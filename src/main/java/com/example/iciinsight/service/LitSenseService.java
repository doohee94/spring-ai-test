package com.example.iciinsight.service;

import com.example.iciinsight.dto.LitSenseDto;
import com.example.iciinsight.dto.LitSenseList;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LitSenseService {

    private final WebClient litSenseWebClient;
    private final WeaviateVectorStore weaviateVectorStore;


    public void addLitSenseDataToVDB(String gene, String drug) {

        Mono<List<LitSenseDto>> litSenseDtoMono = litSenseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", "Are there any links between "+drug+" and " + gene)
                        .queryParam("rerank", "true")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LitSenseDto>>() {
                });

        LitSenseList litSenseList = new LitSenseList(litSenseDtoMono.block());

        List<Document> documents = litSenseList.getLitSenseDtoList().stream()
                .map(t -> new Document(t.getText(), Map.of("pmcid", t.getPmcid() != null ? t.getPmcid() : "UNKNOWN")))
                .toList();


        for  (Document document : documents) {
            System.out.println(document.getMetadata().get("pmcid")+" "+document.getText());
        }

        weaviateVectorStore.add(documents);

    }


}
