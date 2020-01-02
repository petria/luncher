package org.freakz.luncher.lunchfetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freakz.luncher.config.RuntimeConfig;
import org.freakz.luncher.dto.majakka.MajakkaJSON;
import org.freakz.luncher.dto.majakka.MenusForDay;
import org.freakz.luncher.dto.majakka.SetMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@LunchFetcherBean
@RequiredArgsConstructor
@Service
@Slf4j
public class MajakkaLunchFetcher implements LunchFetcher {

    private final ObjectMapper objectMapper;
    private final RuntimeConfig runtimeConfig;

    @Override
    public LunchData fetchLunch() {
        String url = "https://www.amica.fi/modules/json/json/Index?costNumber=2532&language=fi";
	log.debug(">>> url: " + url);
        LunchData lunchData = null;
        try {

            runtimeConfig.setProxy();
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();

            String json = doc.body().text();
//	    log.debug("Json: " + json);
            MajakkaJSON majakkaJSON = objectMapper.readValue(json, MajakkaJSON.class);
            LocalDate now = LocalDate.now();
            String today = String.format("%d-%02d-%02d", now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
            String todayFIN = String.format("%d.%d. %d", now.getDayOfMonth(), now.getMonth().getValue(), now.getYear());
            for (MenusForDay day : majakkaJSON.menusForDays) {
                String date = day.getDate();
		log.debug(date + " <-> " + today);
                if (date.startsWith(today)) {
                    String menuStr = "";
                    for (SetMenu menu : day.setMenus) {
                        menuStr += menu.getName() + ": ";
                        for (String component : menu.getComponents()) {
                            menuStr += component + " | ";
                        }
                        menuStr += "\n";
                    }
                    lunchData = LunchData.builder().menu(menuStr.replaceAll("null", "")).title("Majakka " + todayFIN).build();
                }
            }

        } catch (IOException e) {
            log.error("Lunch error!", e);
        }

        return lunchData;
    }
}
