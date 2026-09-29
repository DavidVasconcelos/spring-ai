package com.eazybytes.support.agent.client;

import com.eazybytes.support.agent.client.dto.MailpitMessage;
import com.eazybytes.support.agent.client.dto.MailpitMessagesResponse;
import com.eazybytes.support.agent.client.dto.ReadUpdate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/v1")
public interface MailpitApi {

    @GetExchange("/search")
    MailpitMessagesResponse searchMessages(
        @RequestParam("query") String query, 
        @RequestParam("limit") int limit
    );

    @GetExchange("/message/{id}")
    MailpitMessage getMessage(@PathVariable("id") String id);

    @PutExchange("/messages")
    void updateMessageStatus(@RequestBody ReadUpdate request);
}