package com.example.iciinsight.service;


import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.AIResponseListDto;
import com.example.iciinsight.dto.PromptLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final ChatClient chatClient;
    private final WeaviateVectorStore weaviateVectorStore;

    public List<AIResponseDTO> getAIResponse(String gene, String drug) throws JsonProcessingException {

        String promptTemplateText = PromptLoader.loadPrompt("drug-gene.prompt");

        PromptTemplate customPromptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template(promptTemplateText)
                .build();

        String question = "Analyze the relationship between "+ drug +" and "+ gene;

        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(weaviateVectorStore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.5).topK(40).build())
                .promptTemplate(customPromptTemplate)
                .build();


        String response = chatClient
                .prompt(question)
                .advisors(questionAnswerAdvisor)
                .call()
                .content();


        List<AIResponseDTO> aiResponseDTOList = new AIResponseListDto(response).getAiResponseDTOList();

        return aiResponseDTOList;

    }
}
