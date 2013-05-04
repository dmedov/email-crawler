package org.parse.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCrawler {
    private final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
    private Pattern pattern;
    private Matcher matcher;
    private ArrayList<String> emails;
    public boolean debugMode;

    EmailCrawler() {
        pattern = Pattern.compile(RE_MAIL);
        emails = new ArrayList<String>();
        debugMode = true;
    }

    public void run(String url, String filterUrlRegex) {
        Document doc = null;
        ArrayList<String> urls = new ArrayList<String>();

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (filterUrlRegex != null) {
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String attr = link.attr("abs:href");
                if (attr.matches(filterUrlRegex)) {
                    debugPrint("%s", attr);
                    urls.add(attr) ;
                }
            }

            for (String findUrl : urls) {
                getEmail(findUrl);
            }

            return;
        }

        getEmail(url);
    }

    private void debugPrint(String msg, Object... args) {
        if (debugMode) {
            System.out.println(String.format(msg, args));
        }
    }

    private void getEmail(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String body = doc.toString();

        matcher = pattern.matcher(body);
        while(matcher.find()) {
            String email = matcher.group(1);
            if(!emails.contains(email)) {
                emails.add(email);
                debugPrint("%s", email);
            }
        }
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setDebug(boolean mode) {
        debugMode = mode;
    }
}
