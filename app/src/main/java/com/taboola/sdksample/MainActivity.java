package com.taboola.sdksample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taboola.sdksample.summary.SummaryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openSummaryPage();
    }

    private void openSummaryPage() {
        Intent intent = new Intent(this, SummaryActivity.class);
        startActivity(intent);
    }

}
