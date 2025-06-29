package com.example.iciinsight.dto;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public class PromptLoader {
    public static String loadPrompt(String filePath) {
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load prompt from file: " + filePath, e);
        }
    }
}
