package com.example.brandservice.configuration;// PaypalServerSdkConfig.java

import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;
import com.paypal.sdk.Environment;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    @Value("${PAYPAL_CLIENT_ID}")
    private String clientId;

    @Value("${PAYPAL_CLIENT_SECRET}")
    private String clientSecret;

    @Bean
    public PaypalServerSdkClient paypalServerSdkClient() {
        return new PaypalServerSdkClient.Builder()
                .loggingConfig(builder -> builder
                        .level(Level.DEBUG)
                        .requestConfig(logConfigBuilder -> logConfigBuilder.body(true))
                        .responseConfig(logConfigBuilder -> logConfigBuilder.headers(true)))
                .httpClientConfig(configBuilder -> configBuilder.timeout(0))
                .environment(Environment.SANDBOX)
                .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(
                        clientId,
                        clientSecret).build())
                .build();
    }
}
