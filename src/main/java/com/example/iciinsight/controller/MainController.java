package com.example.iciinsight.controller;

import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.DrugDto;
import com.example.iciinsight.service.MainService;
import com.example.iciinsight.service.DrugService;
import com.example.iciinsight.service.LitSenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Controller
@RequiredArgsConstructor
public class MainController {

    private final LitSenseService litSenseService;
    private final MainService mainService;
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

        List<AIResponseDTO> results  = mainService.getDetails(gene, drug);

        model.addAttribute("gene", gene);
        model.addAttribute("analysisResults", results);

        return "gene/analysis";
    }

}
