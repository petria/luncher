package org.freakz.luncher.lunchfetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.freakz.luncher.config.RuntimeConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@LunchFetcherBean
@RequiredArgsConstructor
@Service
@Slf4j
public class MyrskyLunchFetcher implements LunchFetcher {

    private final ObjectMapper objectMapper;
    private final RuntimeConfig runtimeConfig;

    @Override
    public LunchData fetchLunch() {
        LocalDate now = LocalDate.now();
        String today = String.format("%d-%d-%d", now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
        String todayFIN = String.format("%d.%d. %d", now.getDayOfMonth(), now.getMonth().getValue(), now.getYear());
        String url = String.format("https://www.sodexo.fi/ruokalistat/output/daily_json/232/%d-%02d-%02d", now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
        LunchData lunchData = null;
        try {

            runtimeConfig.setProxy();
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            String menuStr = "";

            String json = doc.body().text();
            Map map =  objectMapper.readValue(json, Map.class);
            LinkedHashMap courses = (LinkedHashMap) map.get("courses");
            for (Object obj : courses.values()) {
                if (obj instanceof LinkedHashMap) {
                    Map menuMap = (Map) obj;
                    menuStr += menuMap.get("category") + ": " + menuMap.get("title_fi");
                    menuStr += "\n";
                }
            }
            lunchData = LunchData.builder().menu(menuStr.replaceAll("null", "")).title( "Myrsky " + todayFIN).build();
        } catch (IOException e) {
            log.error("Lunch error!", e);
        }

        return lunchData;
    }
}
