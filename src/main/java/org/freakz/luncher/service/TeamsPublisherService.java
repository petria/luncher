package org.freakz.luncher.service;

import org.freakz.luncher.config.RuntimeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeamsPublisherService {

    private final RuntimeConfig runtimeConfig;

    public void publishToTeams(String message) {

        String body = String.format("{\"text\": \"<PRE>%s</PRE>\" }", message);
        String url = runtimeConfig.getTeamsWebHookUrl();
        if (url == null || url.isEmpty()) {
            log.error("No URL defined!");
            return;
        }
        try {

            runtimeConfig.setProxy();
            Document doc = Jsoup.connect(url).method(Connection.Method.POST).requestBody(body).post();
            String response = doc.body().text();
            log.debug("Response: {}", response);

        } catch (IOException e) {
            log.error("TeamPublish POST error", e);
        }
    }

}
