package com.eazybytes.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class SpringAiApplicationTests {

	@MockitoBean
	private VectorStore vectorStore;

	@MockitoBean(name = "hrChatClient")
	private ChatClient hrChatClient;

	@MockitoBean(name = "helpDeskChatClient")
	private ChatClient helpDeskChatClient;

	@MockitoBean(name = "customerServiceClient")
	private ChatClient customerServiceClient;

	@MockitoBean(name = "openChatClient")
	private ChatClient openChatClient;

	@MockitoBean(name = "timeChatClient")
	private ChatClient timeChatClient;

	@MockitoBean(name = "webSearchRAGChatClient")
	private ChatClient webSearchRAGChatClient;

	@MockitoBean(name = "chatMemoryChatClient")
	private ChatClient chatMemoryChatClient;


	@Test
	void contextLoads() {
	}

}
