package com.magicFridgeAi.MagicFridgeAi.service;

import com.magicFridgeAi.MagicFridgeAi.enums.Meal;
import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final WebClient webClient;

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    private String apiKey = System.getenv("API_KEY");

    public Mono<String> generateRecipe(List<FoodItem> foodItems, Meal meal) {
        String alimentos = foodItems.stream()
                .map(item -> String.format("Nome: %s, Quantidade: %d, Categoria: %s, Validade: %s",
                        item.getNome(), item.getQuantidade(), item.getCategoria(), item.getValidade()))
                .collect(Collectors.joining("\n"));

        String prompt = "Com os seguintes ingredientes disponíveis na geladeira: " + alimentos +
                " crie uma receita prática. A receita deve ser uma sugestão realista baseada no que foi listado. " +
                "\nCrie a receita com base no tipo de refeição que o usuário enviou: " + meal +
                "\nNão é preciso usar todos os itens. Apenas me dê uma ideia de prato que possa ser feito.";

        Map<String, Object> requestBody = Map.of(
                "model", "gemini-1.5-flash",
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("X-goog-api-key", apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("Resposta completa da API: " + response);

                    var candidates = (List<Map<String, Object>>) response.get("candidates");
                    if (candidates != null && !candidates.isEmpty()) {
                        var content = (Map<String, Object>) candidates.get(0).get("content");
                        if (content != null) {
                            var parts = (List<Map<String, Object>>) content.get("parts");
                            if (parts != null && !parts.isEmpty()) {
                                return parts.get(0).get("text").toString();
                            }
                        }
                    }

                    var error = (Map<String, Object>) response.get("error");
                    if (error != null) {
                        return "Erro da API: " + error.get("message").toString();
                    }

                    return "Nenhuma receita foi gerada ou a resposta da API não está no formato esperado.";
                })
                .doOnError(throwable -> {
                    System.err.println("Erro na requisição WebClient: " + throwable.getMessage());
                });
    }
}
