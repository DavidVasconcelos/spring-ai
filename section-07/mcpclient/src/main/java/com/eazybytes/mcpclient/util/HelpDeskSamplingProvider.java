package com.eazybytes.mcpclient.util;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.annotation.McpSampling;
import org.springframework.stereotype.Component;

/**
 * Handles MCP <b>sampling</b> requests. When a tool on the MCP server calls
 * {@code ctx.sample(...)}, the server forwards a {@link McpSchema.CreateMessageRequest} to this
 * client. We run the requested completion against our own LLM and return the result, giving the
 * server LLM access without it needing its own API key.
 * <p>
 * IMPORTANT: we inject the low-level {@link ChatModel} and NOT a {@code ChatClient}. A
 * {@code ChatClient} is wired up with the MCP tool callbacks, so using it here could trigger
 * another tool call that issues yet another sampling request -> infinite loop.
 */
@Component
public class HelpDeskSamplingProvider {

  private static final Logger logger = LoggerFactory.getLogger(HelpDeskSamplingProvider.class);

  private final ChatModel chatModel;

  public HelpDeskSamplingProvider(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  @McpSampling(clients = "eazybytes")
  public McpSchema.CreateMessageResult handleSamplingRequest(
      McpSchema.CreateMessageRequest request) {
    logger.info("Received MCP sampling request from server. System prompt: {}",
        request.systemPrompt());

    // Translate the MCP sampling messages into Spring AI prompt messages.
    List<Message> messages = Optional.ofNullable(request.systemPrompt())
        .map(systemPrompt -> new ArrayList<Message>(List.of(new SystemMessage(systemPrompt))))
        .orElseGet(ArrayList::new);

    String userMessage = request.messages().stream()
        .filter(message -> message.content() instanceof TextContent
            && message.role().name().equalsIgnoreCase(Role.USER.name()))
        .map(message -> ((TextContent) message.content()).text())
        .collect(Collectors.joining("\n"));

    messages.add(new UserMessage(userMessage));

    // Call the LLM directly via ChatModel to avoid re-triggering MCP tools.
    ChatResponse response = chatModel.call(new Prompt(messages));
    if (response.getResult() == null) {
      throw new IllegalStateException("LLM returned no result for the MCP sampling request");
    }

    String generatedText = Objects.requireNonNullElse(response.getResult().getOutput().getText(),
        "");
    String model = response.getMetadata().getModel();
    logger.info("LLM produced sampling response using model {}: {}", model, generatedText);

    return McpSchema.CreateMessageResult.builder(Role.ASSISTANT, generatedText, model).build();
  }
}
