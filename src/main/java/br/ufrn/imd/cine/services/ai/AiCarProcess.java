package br.ufrn.imd.cine.services.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufrn.imd.cine.model.ai.PromptMovieRecomendationRecord;
import br.ufrn.imd.cineframework.models.movie.Movie;
import br.ufrn.imd.cineframework.services.ai.AIModel;
import br.ufrn.imd.cineframework.services.ai.AIProcessor;

public class AiCarProcess extends AIProcessor {

    public AiCarProcess(AIModel aIModel) {
        super(aIModel);
    }

    @Override
    protected String processPrompt(Object r) {
        return mountPrompt((PromptMovieRecomendationRecord) r).replace("\"", "\\\"");
    }

    @Override
    protected String sendRequest(String requestPrompt) {
        return super.aiProcessor.process(requestPrompt);
    }

    @Override
    protected String processResponse(String prompt) {
        try {
            return String.join(", ",
                    extractContentNumbers(prompt).stream().map((n) -> n + "").collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private List<Long> extractContentNumbers(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.readTree(jsonString);

        JsonNode choicesNode = rootNode.path("choices");
        if (!choicesNode.isArray() || choicesNode.size() == 0) {
            throw new IllegalArgumentException("Campo 'choices' está ausente ou vazio.");
        }

        JsonNode firstChoice = choicesNode.get(0);
        JsonNode messageNode = firstChoice.path("message");
        JsonNode contentNode = messageNode.path("content");

        if (contentNode.isMissingNode() || !contentNode.isTextual()) {
            throw new IllegalArgumentException("Campo 'content' está ausente ou não é textual.");
        }

        String content = contentNode.asText();

        String[] numberStrings = content.split(",");
        List<Long> numbers = new ArrayList<>();

        for (String numStr : numberStrings) {
            String trimmed = numStr.trim();
            if (!trimmed.isEmpty()) {
                try {
                    Long number = Long.parseLong(trimmed);
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    // Ignorar números inválidos
                }
            }
        }

        return numbers;
    }

    private String mountPrompt(PromptMovieRecomendationRecord r) {
        StringBuilder sb = new StringBuilder();

        sb.append("COM BASE NOS SEGUINTES FILMES ASSISTIDOS POR CARROS EM UM DRIVE-IN:");
        for (Movie m : r.moviesAlreadyBought()) {
            sb.append("id: ");
            sb.append(m.getId());
            sb.append(", title: ");
            sb.append(m.getTitle());
            sb.append(", synopsis: ");
            sb.append(m.getSynopsis());
            sb.append(", genre: ");
            sb.append(m.getGenre().getDescription());
            sb.append(". ");
        }

        sb.append("ESSES SÃO TODOS OS FILMES DISPONÍVEIS NO DRIVE-IN: ");
        for (Movie m : r.moviesAvailable()) {
            sb.append("id: ");
            sb.append(m.getId());
            sb.append(", title: ");
            sb.append(m.getTitle());
            sb.append(", synopsis: ");
            sb.append(m.getSynopsis());
            sb.append(", genre: ");
            sb.append(m.getGenre().getDescription());
            sb.append(". ");
        }

        sb.append(
                "Me retorne todos os filmes que os carros talvez gostariam de assistir, considerando que no carro podem ter várias pessoas com gostos diferentes, logo as recomendações não podem ser tão distoantes, no máximo 10, E DO MESMO GENERO, DE PREFERÊNCIA como recomendação SEM REPETIR O QUE JÁ FOI ASSISTIDO.");
        sb.append("MANDE ESTRITAMENTE EM FORMATO DE LISTA DE IDs, no formato: id1, id2, id3, ... ");

        return sb.toString();
    }
}
