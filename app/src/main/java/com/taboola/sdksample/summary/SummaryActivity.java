package com.taboola.sdksample.summary;

import android.app.Activity;
import android.os.Bundle;

import com.taboola.android.TaboolaWidget;
import com.taboola.sdksample.R;

import java.util.HashMap;

public class SummaryActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        TaboolaWidget taboolaWidget = findViewById(R.id.taboola_view);
        HashMap<String, String> optionalPageCommands = new HashMap<>();
        optionalPageCommands.put("useOnlineTemplate", "true");
        taboolaWidget.setExtraProperties(optionalPageCommands);
        taboolaWidget.fetchContent();
    }

}
