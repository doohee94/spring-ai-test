package com.example.iciinsight.controller;

import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.service.AIService;
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

    @GetMapping
    public String index(Model model) {
        return "gene/search";
    }

    @GetMapping("/gene/{geneName}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> checkGene(@PathVariable String geneName) {

        boolean exists = litSenseService.checkGene(geneName);
        Map<String, String> response = new HashMap<>();
        response.put("status", exists ? "ok" : "error");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/gene/{geneName}/details")
    public String details(@PathVariable String geneName, Model model) throws JsonProcessingException {

        litSenseService.addLitSenseDataToVDB(geneName);
        List<AIResponseDTO> results  =aiService.getAIResponse(geneName);

        model.addAttribute("geneName", geneName);
        model.addAttribute("analysisResults", results);

        return "gene/analysis";
    }

}
