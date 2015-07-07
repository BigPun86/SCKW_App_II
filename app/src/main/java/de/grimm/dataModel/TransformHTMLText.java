package de.grimm.dataModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Adel on 05.07.2015.
 */
public class TransformHTMLText {

    public static String br2nl(String html) {
        Document document = Jsoup.parse(html);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        return document.text().replace("\\n", "\n");
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
