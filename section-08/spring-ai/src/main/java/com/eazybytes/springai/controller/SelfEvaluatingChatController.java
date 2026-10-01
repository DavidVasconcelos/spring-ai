package com.eazybytes.springai.controller;

import com.eazybytes.springai.exception.InvalidAnswerException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/evaluate")
public class SelfEvaluatingChatController {

  private final ChatClient openAiChatClient;
  private final FactCheckingEvaluator factCheckingEvaluator;

  @Value("classpath:/promptTemplates/hrPolicy.st")
  Resource hrPolicyTemplate;

  public SelfEvaluatingChatController(
      @Qualifier("openAiChatBuilder") ChatClient.Builder openAiBuilder,
      @Qualifier("ollamaChatBuilder") ChatClient.Builder ollamaChatBuilder,
      @Value("classpath:/promptTemplates/factCheck.st") Resource factCheckTemplate)
      throws IOException {
    this.openAiChatClient = openAiBuilder
        .defaultAdvisors(new SimpleLoggerAdvisor())
        .build();
    this.factCheckingEvaluator = FactCheckingEvaluator
        .builder(ollamaChatBuilder.defaultAdvisors(new SimpleLoggerAdvisor()))
        .evaluationPrompt(factCheckTemplate.getContentAsString(Charset.defaultCharset()))
        .build();
  }

  @Retryable(retryFor =  InvalidAnswerException.class, maxAttempts = 5)
  @GetMapping("/chat")
  public String chat(@RequestParam("message") String message) {
    String aiResponse = openAiChatClient.prompt()
        .user(message)
        .call()
        .content();
    validateAnswer(message, aiResponse);
    return aiResponse;
  }

  @GetMapping("/prompt-stuffing")
  public String promptStuffing(@RequestParam("message") String message) {
    return openAiChatClient.prompt()
        .system(hrPolicyTemplate)
        .user(message)
        .call()
        .content();
  }

  private void validateAnswer(String message, String answer) {
    EvaluationRequest evaluationRequest = new EvaluationRequest(message, List.of(), answer);
    EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
    if (!evaluationResponse.isPass()) {
      throw new InvalidAnswerException(message, answer);
    }
  }

  @Recover
  private String recover(InvalidAnswerException exception) {
    log.info("Recovering from {}", exception.getMessage());
    return "I'm sorry, I couldn't answer your question. Please try rephrasing it.";
  }
}
