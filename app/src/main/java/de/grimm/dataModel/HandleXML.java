package de.grimm.dataModel;

/**
 * Created by Adel on 27.06.2015.
 */

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HandleXML {
    public volatile boolean parsingComplete = true;
    int newsTitles, newsDescriptions, newsImage = 0;
    List<String> listTitles = new ArrayList<String>();
    List<String> listDesc = new ArrayList<String>();
    List<String> listImages = new ArrayList<String>();
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;

    public HandleXML(String url) {
        this.urlString = url;
    }

    public List<String> getListImages() {
        return listImages;
    }

    public void setListImages(List<String> list) {
        this.listImages = list;
    }

    public List<String> getListDesc() {
        return listDesc;
    }

    public void setListDesc(List<String> listDesc) {
        this.listDesc = listDesc;
    }

    public List<String> getListTitles() {
        return listTitles;
    }

    public void setListTitle(List<String> list) {
        this.listTitles = list;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("title")) {
                            listTitles.add(text);
                            setListTitle(listTitles);
                            newsTitles++;
//
                        } else if (name.equals("description")) {
//                            listDesc.add(TransformHTMLText.br2nl(text));
                            listDesc.add(TransformHTMLText.html2text(text));
                            setListDesc(listDesc);
                            newsDescriptions++;
                        } else {
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    Log.d("Parse", stream.toString());
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }
}