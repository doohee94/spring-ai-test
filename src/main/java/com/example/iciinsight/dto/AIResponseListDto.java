package com.example.iciinsight.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AIResponseListDto {

    private List<AIResponseDTO> aiResponseDTOList;

    public AIResponseListDto(String response) throws JsonProcessingException {

        response = response.replaceAll("```", "");
        response = response.replaceAll("json", "");

        ObjectMapper mapper = new ObjectMapper();

        this.aiResponseDTOList = mapper.readValue(response, new TypeReference<>() {});

    }
}
