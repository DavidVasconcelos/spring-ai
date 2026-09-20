package com.eazybytes.springai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.eazybytes.springai.controller.ChatController;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class SpringAiApplicationTests {

  @Autowired
  private ChatController chatController;

  @Autowired
  private ChatModel chatModel;

  private ChatClient chatClient;
  private RelevancyEvaluator relevancyEvaluator;

  @Value("${test.relevancy.min-score:0.7}")
  private float minRelevancyScore;

  @BeforeEach
  void setup() {
    Builder builder = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());

    this.chatClient = builder.build();
    this.relevancyEvaluator = new RelevancyEvaluator(builder);
  }

  @Test
  @DisplayName("Should return relevant response for basic geography question")
  @Timeout(value = 30, unit = TimeUnit.SECONDS)
  void evaluateChatControllerResponseRelevancy() {
    String question = "What is the capital of Brazil ?";

    String aiResponse = chatController.chat(question);
    EvaluationRequest evaluationRequest = new EvaluationRequest(question, aiResponse);
    EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);

    assertAll(
        () -> assertThat(aiResponse).isNotBlank(),
        () -> assertThat(evaluationResponse.isPass())
            .withFailMessage(
                """
                    ========================================
                    The answer was not considered relevant.
                    Question: "%s"
                    Response: "%s"
                    ========================================
                    """, question, aiResponse)
            .isTrue(),
        () -> assertThat(evaluationResponse.getScore())
            .withFailMessage(
                """
                    ========================================
                    The score %.2f is lower than the minimum required %.2f.
                    Question: "%s"
                    Response: "%s"
                    ========================================
                    """, evaluationResponse.getScore(), minRelevancyScore, question, aiResponse)
            .isGreaterThan(minRelevancyScore)
    );
  }
}
