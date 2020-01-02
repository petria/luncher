package org.freakz.luncher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.freakz.luncher.config.RuntimeConfig;
import org.freakz.luncher.lunchfetcher.LunchData;
import org.freakz.luncher.lunchfetcher.LunchFetcher;
import org.freakz.luncher.lunchfetcher.LunchFetcherBean;
import org.freakz.luncher.service.TeamsPublisherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class MainRunner implements CommandLineRunner {

    private final ApplicationContext context;
    private final RuntimeConfig runtimeConfig;
    private final TeamsPublisherService publisherService;

    @Override
    public void run(String... args) throws Exception {
        String messageToTeams = "";
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(LunchFetcherBean.class);
        for (Object bean : beansWithAnnotation.values()) {
            LunchFetcher lunchFetcher = (LunchFetcher) bean;
	    log.debug("Fetcing lunch: " + lunchFetcher);
            LunchData data = lunchFetcher.fetchLunch();
            if (data != null) {
                messageToTeams += "<b>" + data.getTitle() + "</b>\n";
                messageToTeams += data.getMenu();
            }
        }
        if (!messageToTeams.isEmpty()) {
            if (runtimeConfig.isPublishToTeams()) {
                publisherService.publishToTeams(messageToTeams);
            } else {
                System.out.printf("%s", messageToTeams);
            }
        }
        SpringApplication.exit(context, () -> 0);

    }

}
