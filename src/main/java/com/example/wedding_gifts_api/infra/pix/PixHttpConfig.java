package com.example.wedding_gifts_api.infra.pix;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wedding_gifts_api.core.domain.exceptions.payment.PaymentHttpException;
import com.example.wedding_gifts_api.core.domain.model.OAuthPsb;

@Component
public class PixHttpConfig {

    @Value("${api.payment.pix.developer-key}")
    private String developerKey;

    private final Duration DURATION = Duration.ofSeconds(15L);

    private String developerKeyParam = "?gw-dev-app-key="; 

    public HttpClient getClient() throws Exception {
        try {
            return HttpClient.newBuilder()
                .connectTimeout(DURATION)
                .sslContext(SSLContext.getDefault())
                //.sslContext(sslConfig.createSslContext())
                .build();    
        } catch (Exception e) {
            throw new PaymentHttpException(" SSL protocol not alowed, error in generate java.net.http.HttpClient", e);
        }
    }

    public HttpRequest getRequest(OAuthPsb token, String url, String method, String body) throws Exception {
        try{
            return HttpRequest.newBuilder()
                .uri(new URI(url+developerKeyParam+developerKey))
                .method(method, BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .header("Authorization", token.getTokenType()+" "+token.getAuthToken())
                .build();
        } catch (URISyntaxException e) {
            throw new PaymentHttpException("HTTP URL is invalid", e);
        } catch (Exception e) {
            throw new PaymentHttpException("Some in generate java.net.http.HttpRequest", e);
        }
    }

    public HttpRequest getRequestToOauth(String basicToken, String url, String body) throws Exception {
        try{
            return HttpRequest.newBuilder()
                    .uri(new URI(url+developerKeyParam+developerKey))
                    .POST(BodyPublishers.ofString(body))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + basicToken)
                    .build();
        } catch (Exception e) {
            throw new PaymentHttpException("HTTP URL is invalid", e);
        }
    }
    
}
