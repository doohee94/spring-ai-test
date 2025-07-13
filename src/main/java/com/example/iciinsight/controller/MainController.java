package com.example.iciinsight.controller;

import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.DrugDto;
import com.example.iciinsight.service.AIService;
import com.example.iciinsight.service.DrugService;
import com.example.iciinsight.service.LitSenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final LitSenseService litSenseService;
    private final AIService aiService;
    private final DrugService drugService;

    @GetMapping
    public String index(Model model) {
        return "gene/search";
    }


    @GetMapping("/gene/{gene}")
    @ResponseBody
    public List<DrugDto> test(@PathVariable String gene) {
        return drugService.getDrugList(gene);
    }

    @GetMapping("/gene/{gene}/details")
    public String details(@PathVariable String gene,String drug, Model model) throws JsonProcessingException {

        litSenseService.addLitSenseDataToVDB(gene, drug);
        List<AIResponseDTO> results  =aiService.getAIResponse(gene, drug);

        model.addAttribute("gene", gene);
        model.addAttribute("analysisResults", results);

        return "gene/analysis";
    }

}
