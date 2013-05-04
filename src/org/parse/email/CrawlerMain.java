package org.parse.email;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CrawlerMain {
    public static final String MAIN_URL = "http://www.edu.ru/abitur/act.2/index.php?sort=1&show_results=3000" +
                                     "&act=2&name=&town_=&vid=0&naz_spe=&okso_=" +
                                     "&fgos_=&fof_=0&gos=0&akk=0&vkaf=0&cours=0";

    public static final String FILTER_URL_REGEX = ".*act.3/.*";
    public static final String FILE_NAME = "C:\\Users\\user8\\emails.txt";

    public static void main(String[] args) {
        EmailCrawler ec = new EmailCrawler();

        ec.run(MAIN_URL, FILTER_URL_REGEX);
        ArrayList<String> emails = ec.getEmails();
        saveEmails(ec.getEmails(), FILE_NAME);
        System.out.println( String.valueOf(emails.size())
                            + " writed to file "
                            + FILE_NAME);
    }

    public static void saveEmails(ArrayList<String> emails, String filename) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String email : emails) {
            pw.println(email);
        }

        pw.close();
    }
}


