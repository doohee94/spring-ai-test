package com.example.iciinsight.controller;

import com.example.iciinsight.dto.AIResponseDTO;
import com.example.iciinsight.dto.DrugDto;
import com.example.iciinsight.dto.LitSenseDto;
import com.example.iciinsight.service.MainService;
import com.example.iciinsight.service.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final MainService mainService;

    @GetMapping("/test/drug")
    public List<DrugDto> getDrugList(@RequestParam String gene) {
        return testService.getDrugList(gene);
    }

    @GetMapping("/test/litsense")
    public List<LitSenseDto> getLitsenseList(@RequestParam String gene, @RequestParam String drug){
        return testService.getLitsenseList(gene, drug);
    }

    @GetMapping("/test/ai")
    public List<AIResponseDTO> getAiResponse(@RequestParam String gene, @RequestParam String drug) throws JsonProcessingException {
        return mainService.getDetails(gene, drug);
    }

}
