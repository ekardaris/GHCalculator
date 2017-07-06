package nableamea.ghcalculator;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class DosemeterActivity extends TabActivity {

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosemeter);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);

        Intent committeeDosageIntent = new Intent().setClass(this, CommitteeDosageActivity.class);
        TabHost.TabSpec committeeDosageSpec = tabHost.newTabSpec("Committee Dosage").setIndicator("Δοση επιτροπης").setContent(committeeDosageIntent);

        Intent ghdIntent = new Intent().setClass(this, GHDActivity.class);
        TabHost.TabSpec ghdTabSpec = tabHost.newTabSpec("GHD PWS SGA").setIndicator("GHD PWS SGA").setContent(ghdIntent);

        Intent turnerIntent = new Intent().setClass(this, TurnerActivity.class);
        TabHost.TabSpec turnerTabSpec = tabHost.newTabSpec("IRC Turner").setIndicator("IRC Turner").setContent(turnerIntent);

        tabHost.addTab(committeeDosageSpec);
        tabHost.addTab(ghdTabSpec);
        tabHost.addTab(turnerTabSpec);
    }

    @Override
    public void onBackPressed() {
    }
}
