package de.grimm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import adelgrimm.sckw_appii.R;

/**
 * Created by adel on 08.07.15.
 */
public class TheNews extends FragmentActivity {

    // get Intents ...
    // get position from Fragment X
    // get Image would be great!!!!
    // Show News nice formatted

    TextView titleView, textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_news);

        String title, text;
        Intent i = getIntent();
        title = i.getStringExtra("TIT");
        text = i.getStringExtra("TXT");

        titleView = (TextView) findViewById(R.id.titleView);
        textView = (TextView) findViewById(R.id.textView);

        if (title != null && text != null) {
            titleView.setText(title);
            textView.setText(text);
        }

    }
}
