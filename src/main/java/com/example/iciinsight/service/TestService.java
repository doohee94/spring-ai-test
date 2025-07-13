package com.example.iciinsight.service;


import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.DrugDto;
import com.example.iciinsight.dto.LitSenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {

    private final DrugService drugService;
    private final LitSenseService litSenseService;


    public List<LitSenseDto> getLitsenseList(String gene, String drug) {

        return  litSenseService.getLitsenseList(gene, drug).getLitSenseDtoList();
    }

    public List<DrugDto> getDrugList(String gene) {
        return drugService.getDrugList(gene);
    }

    public List<AIResponseDTO> getAiResponse(String gene, String drug) {
        return null;
    }
}
