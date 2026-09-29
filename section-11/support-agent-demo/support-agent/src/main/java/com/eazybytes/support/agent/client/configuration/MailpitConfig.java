package com.eazybytes.support.agent.client.configuration;

import com.eazybytes.support.agent.client.MailpitApi;
import com.eazybytes.support.agent.configuration.InboxProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class MailpitConfig {

    @Bean
    public MailpitApi mailpitApi(RestClient.Builder builder, InboxProperties props) {
        RestClient restClient = builder.baseUrl(props.baseUrl()).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        
        return factory.createClient(MailpitApi.class);
    }
}