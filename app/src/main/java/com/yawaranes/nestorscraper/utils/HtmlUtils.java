package com.yawaranes.nestorscraper.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlUtils {

    private HtmlUtils() {
    }

    /**
     * Extract the attribute href links of <a> tags
     * @param html The HTML source code
     * @return The List of found links
     */
    public static List<String> extractLinksFromHtml(String html) {
        List<String> linkList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements links = document.select("a[href]");
        for (Element link : links) {
            linkList.add(link.attr("href"));
        }
        return linkList;
    }
}
