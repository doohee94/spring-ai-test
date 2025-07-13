package com.example.iciinsight.service;

import com.example.iciinsight.dto.DrugDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DrugService {

    private final WebClient DGIdbWebClient;

    public List<DrugDto> getDrugList(String gene){

        String query = """
                {
                  genes(names: ["%s"]) {
                    nodes {
                      interactions {
                        drug {
                          name
                          approved
                        }                
                      } 
                    }
                  }
                }
                """.formatted(gene);

        Map<String, Object> requestBody = Map.of("query", query);

        return DGIdbWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class) // 전체 응답을 Map으로 받음
                .map(this::extractDrugsFromMap)
                .block();
    }

    private List<DrugDto> extractDrugsFromMap(Map<String, Object> response) {
        List<DrugDto> drugs = new ArrayList<>();

        try {
            List<Map<String, Object>> nodes = (List<Map<String, Object>>)
                    ((Map<String, Object>) ((Map<String, Object>) response.get("data")).get("genes")).get("nodes");

            for (Map<String, Object> node : nodes) {
                List<Map<String, Object>> interactions = (List<Map<String, Object>>) node.get("interactions");
                for (Map<String, Object> interaction : interactions) {
                    Map<String, Object> drugMap = (Map<String, Object>) interaction.get("drug");
                    String name = (String) drugMap.get("name");
                    boolean approved = (Boolean) drugMap.get("approved");
                    drugs.add(new DrugDto(name, approved));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drugs.stream().sorted(Comparator.comparing(DrugDto::getName)).collect(Collectors.toList());
    }


}
