package com.example.iciinsight.service;

import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.LitSenseList;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {


    private final LitSenseService litSenseService;
    private final OpenAIService openAIService;
    private final WeaviateVectorStore weaviateVectorStore;

    public List<AIResponseDTO> getDetails(String gene, String drug) throws JsonProcessingException {

        LitSenseList listSenseList = litSenseService.getLitsenseList(gene, drug);
        weaviateVectorStore.add(listSenseList.convertDocument());

        return openAIService.getAIResponse(gene, drug);

    }

}
