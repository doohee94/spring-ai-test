package com.example.iciinsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class LitSenseList {

    private List<LitSenseDto> litSenseDtoList;

    public List<Document> convertDocument() {
        return litSenseDtoList.stream()
                .map(t -> new Document(t.getText(), Map.of("pmcid", t.getPmcid() != null ? t.getPmcid() : "UNKNOWN")))
                .toList();
    }
}
