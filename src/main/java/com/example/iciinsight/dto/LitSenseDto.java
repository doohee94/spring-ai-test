package com.example.iciinsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LitSenseDto {

    private String pmcid;
    private String section;
    private Float score;
    private String text;
    private Long pmid;
    private String[] annotations;

}
