package de.grimm.dataModel.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.grimm.dataModel.HandleXML;

/**
 * Created by Adel on 05.07.2015.
 */
public class DownloadService extends IntentService {

    public static final String PARAM_TITLES_VAL = "Titles";
    public static final String PARAM_DESCRIPTION_VAL = "Description";


    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    private static final String TAG = "DownloadService";
    static int itemTitlesSize, itemDescriptionsSize, itemImagesSize;
    private ArrayList<String> itemImages, itemDescriptions, itemTitles;


    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        SystemClock.sleep(3000); // 3 seconds

        // Report Status From IntentService to Activity
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String[] url = {intent.getStringExtra("urlAktive"), intent.getStringExtra("urlVerein"), intent.getStringExtra("urlJunioren")};

        Bundle bundle = new Bundle();

        for (String s : url) {
            if (!TextUtils.isEmpty(s)) {
            /* Update UI: Download Service is Running */
                receiver.send(STATUS_RUNNING, Bundle.EMPTY);

                try {
                    Map<String, ArrayList<String>> map = downloadData(s);
                    ArrayList<String> resultTitles = map.get(PARAM_TITLES_VAL);
                    ArrayList<String> resultDescription = map.get(PARAM_DESCRIPTION_VAL);

                /* Sending result back to activity */
                    if (null != resultTitles && resultTitles.size() > 0) {
                        bundle.putStringArrayList("resultTitle", resultTitles);
                        bundle.putStringArrayList("resultDescription", resultDescription);

                        // Gebe dem receiver einen Schluessel mit um in SplashScreen die News voneinander zu trennen fuer die verschiedenen Views/Fragmente NEWS
                        if (s.equals(url[0])) {
                            bundle.putInt("newsKey", 1);
                        } else if (s.equals(url[1])) {
                            bundle.putInt("newsKey", 2);
                        } else if (s.equals(url[2])) {
                            bundle.putInt("newsKey", 3);
                        }
                        receiver.send(STATUS_FINISHED, bundle);
                    }
                } catch (Exception e) {

                /* Sending error message back to activity */
                    bundle.putString(Intent.EXTRA_TEXT, e.toString());
                    receiver.send(STATUS_ERROR, bundle);
                }
            }
            this.stopSelf();
        }

    }

    private Map<String, ArrayList<String>> downloadData(String url) {

        HandleXML handleXML = new HandleXML(url);
        handleXML.fetchXML();

        while (handleXML.parsingComplete) {
            // ListView Clicked item index
            itemTitlesSize = handleXML.getListTitles().size();
            itemDescriptionsSize = handleXML.getListDesc().size();
            itemTitles = handleXML.getListTitles();
            itemDescriptions = handleXML.getListDesc();
            itemImages = handleXML.getListImages();
        }


        Map<String, ArrayList<String>> map = new HashMap();
        map.put(PARAM_TITLES_VAL, itemTitles);
        map.put(PARAM_DESCRIPTION_VAL, itemDescriptions);

        return map;
    }


}