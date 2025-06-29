package com.example.iciinsight.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AIResponseDTO {

    private String type;
    private int score;
    private String confidence;
    private String scoreReason;
    private String confidenceReason;
    private List<String> sources;
    private String inconsistencies;

}
