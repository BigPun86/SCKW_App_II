package adelgrimm.sckw_appii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.grimm.dataModel.DataObjectContainer;
import de.grimm.dataModel.intentService.DownloadResultReceiver;
import de.grimm.dataModel.intentService.DownloadService;

/**
 * Created by Adel on 05.07.2015.
 */
public class SplashScreen extends Activity implements DownloadResultReceiver.Receiver {

    public static final String TITLES_AKTIVE = "TITLES_A";
    public static final String DESCRIPTIONS_AKTIVE = "DESC_A";
    public static final String TITLES_VEREIN = "TITLES_V";
    public static final String DESCRIPTIONS_VEREIN = "DESC_V";
    public static final String TITLES_JUNIOREN = "TITLES_J";
    public static final String DESCRIPTIONS_JUNIOREN = "DESC_J";
    static boolean loading = false;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 100;
    private static String BLOG_AKTIVE_NEWS_URL = "http://sckw.de/rss/blog/Aktive";
    private static String BLOG_VEREIN_NEWS_URL = "http://sckw.de/rss/blog/Verein";
    private static String BLOG_JUNIOREN_NEWS_URL = "http://sckw.de/rss/blog/Junioren";
    DataObjectContainer dataObjectContainer;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private DownloadResultReceiver mReceiver;
    private String size;
    private String[] titlesArray, descArray;
    private String[] titlesForAktive, textForAktive, titlesForVerein, textForVerein, titlesForJunioren, textForJunioren;
    private int newsKey;

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

                titlesArray = resultData.getStringArrayList("resultTitle").toArray(new String[resultData.getStringArrayList("resultTitle").size()]);
                descArray = resultData.getStringArrayList("resultDescription").toArray(new String[resultData.getStringArrayList("resultDescription").size()]);
                newsKey = resultData.getInt("newsKey");


                switch (newsKey) {

                    case 1:
                        // News for AktiveNewsFragment
                        titlesForAktive = titlesArray;
                        textForAktive = descArray;
                        break;
                    case 2:
                        // News for VereinNewsFragment
                        titlesForVerein = titlesArray;
                        textForVerein = descArray;
                        break;
                    case 3:
                        // News for JuniorenNewsFragment
                        titlesForJunioren = titlesArray;
                        textForJunioren = descArray;
                        break;
                }

                // Newskey is only 3 when all news have bin fetched from Service, Then put in Bundle
                if (newsKey == 3) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra(TITLES_AKTIVE, titlesForAktive);
                    i.putExtra(DESCRIPTIONS_AKTIVE, textForAktive);
                    i.putExtra(TITLES_VEREIN, titlesForVerein);
                    i.putExtra(DESCRIPTIONS_VEREIN, textForVerein);
                    i.putExtra(TITLES_JUNIOREN, titlesForJunioren);
                    i.putExtra(DESCRIPTIONS_JUNIOREN, textForJunioren);
                    startActivity(i);
                }

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