package br.ufrn.imd.cine.services.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.cine.model.ai.PromptMovieRecomendationRecord;
import br.ufrn.imd.cineframework.services.ai.AIService;

@Service
public class CarAIService {

	@Autowired
	private ChatGPT chatGPT;

	private AiCarProcess aiSimpleProcess = new AiCarProcess(chatGPT);

	private AIService aiService = new AIService(aiSimpleProcess);

	public String analysis(PromptMovieRecomendationRecord r) {
		return aiService.analysis(r);
	}
}
