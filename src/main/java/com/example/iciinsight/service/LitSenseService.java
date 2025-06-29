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
    private final WebClient ncbiWebClient;
    private final WeaviateVectorStore weaviateVectorStore;


    public boolean checkGene(String gene){
        boolean result = false;

        Map<String, Object> response = ncbiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("db", "gene")
                        .queryParam("term", gene + "[Gene%20Name] AND Homo sapiens[Organism]")
                        .queryParam("retmode", "json")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(); //

        if (response != null) {
            Map<String, Object> esearchResult = (Map<String, Object>) response.get("esearchresult");
            List<String> idList = (List<String>) esearchResult.get("idlist");
            result = idList != null && !idList.isEmpty();
        }

        return result;

    }

    public void addLitSenseDataToVDB(String gene) {

        Mono<List<LitSenseDto>> litSenseDtoMono = litSenseWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", "Are immune checkpoint inhibitors linked to " + gene)
                        .queryParam("rerank", "true")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LitSenseDto>>() {
                });

        LitSenseList litSenseList = new LitSenseList(litSenseDtoMono.block());

        List<Document> documents = litSenseList.getLitSenseDtoList().stream()
                .map(t -> new Document(t.getText()))
                .toList();


        for  (Document document : documents) {
            System.out.println(document.getText());
        }

        weaviateVectorStore.add(documents);


//        // 4. 텍스트를 기반으로 GPT에 질문
//
//        PromptTemplate customPromptTemplate = PromptTemplate.builder()
//                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
//                .template("""
//                """)
//                .build();
//
//        String question = "Analyze the relationship between immunotherapy and "+ gene;
//
//        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(weaviateVectorStore)
//                .searchRequest(SearchRequest.builder().similarityThreshold(0.5).topK(40).build())
//                .promptTemplate(customPromptTemplate)
//                .build();
//
//
//        String response = chatClient
//                .prompt(question)
//                .advisors(questionAnswerAdvisor)
//                .call()
//                .content();
//
//        // 5. GPT 모델로 실제 질문

//        return response;
    }


}
