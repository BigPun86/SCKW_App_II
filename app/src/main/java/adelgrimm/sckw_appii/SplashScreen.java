package adelgrimm.sckw_appii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.grimm.dataModel.DataObjectContainer;
import de.grimm.dataModel.intentService.DownloadResultReceiver;
import de.grimm.dataModel.intentService.DownloadService;

/**
 * Created by Adel on 05.07.2015.
 */
public class SplashScreen extends Activity implements DownloadResultReceiver.Receiver {

    public static final String TITLES = "TITLES";
    public static final String DESCRIPTIONS = "DESC";
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    static boolean loading = false;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 100;


    private static String BLOG_AKTIVE_NEWS_URL = "http://sckw.de/rss/blog/Aktive";
    private static String BLOG_VEREIN_NEWS_URL = "http://sckw.de/rss/blog/Verein";
    private static String BLOG_JUNIOREN_NEWS_URL = "http://sckw.de/rss/blog/Junioren";
    private DownloadResultReceiver mReceiver;
    private ArrayList<String> listImages, listDescriptions, listTitles;
    private String size;
    String[] titlesArray, descArray;

    DataObjectContainer dataObjectContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        downloadDataService();

        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    loading = true;
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(SPLASH_TIME_OUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                loading = false;

            }
        }).start();


    }

    private void downloadDataService() {
    /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, SplashScreen.this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("urlAktive", BLOG_AKTIVE_NEWS_URL);
        intent.putExtra("urlVerein", BLOG_VEREIN_NEWS_URL);
        intent.putExtra("urlJunioren", BLOG_JUNIOREN_NEWS_URL);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode) {

            case DownloadService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;

            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                listTitles = resultData.getStringArrayList("resultTitle");
                listDescriptions = resultData.getStringArrayList("resultDescription");

                titlesArray = listTitles.toArray(new String[listTitles.size()]);
                descArray = listDescriptions.toArray(new String[listDescriptions.size()]);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(TITLES, titlesArray);
                i.putExtra(DESCRIPTIONS, descArray);
                startActivity(i);

                break;

            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}