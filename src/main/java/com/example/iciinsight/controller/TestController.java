package com.example.iciinsight.controller;

import com.example.iciinsight.dto.DrugDto;
import com.example.iciinsight.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final DrugService drugService;

    @GetMapping("/test/{gene}")
    public List<DrugDto> test(@PathVariable String gene) {
        return drugService.getDrugList(gene);
    }

}
