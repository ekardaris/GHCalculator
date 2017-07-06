package nableamea.ghcalculator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    TextView textView;
    Button doseButton;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.menulogo);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2799D5")));

        String str = "Omnitrope\n Ψηφιακός Δοσολογικός Δίσκος\n\nΣυνιστώμενη Δοσολογία για βρέφη,\n παιδιά και εφήβους";
        SpannableString formattedSpan = new SpannableString(str);
        formattedSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        formattedSpan.setSpan(new AbsoluteSizeSpan(50), 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        formattedSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.omnitrope)), 0, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        formattedSpan.setSpan(new StyleSpan(Typeface.BOLD), 10, 38, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        formattedSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.header)), 10, 38, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        formattedSpan.setSpan(new AbsoluteSizeSpan(40), 10, 38, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        textView = (TextView) findViewById(R.id.doseTextView);
        textView.setText(formattedSpan);

        doseButton = (Button) findViewById(R.id.doseButton);
        doseButton.setBackgroundResource(R.drawable.button_effect);
        doseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, DosemeterActivity.class);
                startActivity(intent);
            }
        });
    }

    public int downloadFile() {
        while (fileSize <= 1000000) {
            fileSize++;

            if (fileSize == 100000) {
                return 10;
            }

            else if (fileSize == 200000) {
                return 20;
            }

            else if (fileSize == 300000) {
                return 30;
            }

            else if (fileSize == 400000) {
                return 40;
            }

            else if (fileSize == 500000) {
                return 50;
            }

            else if (fileSize == 700000) {
                return 70;
            }
            else if (fileSize == 800000) {
                return 80;
            }
        }
        return 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean ShowMenuOption(MenuItem item) {
        return true; //Indicated menu press was handled
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.recommended_dosage:
                intent = new Intent(MainMenuActivity.this, RecommendedDosageActivity.class);
                startActivity(intent);
                return true;
            case R.id.special_precaution:
                intent = new Intent(MainMenuActivity.this, SpecialPrecautionActivity.class);
                startActivity(intent);
                return true;
            case R.id.information:
                intent = new Intent(MainMenuActivity.this, InformationActivity.class);
                startActivity(intent);
                return true;
            case R.id.patient_management:
                progressBar = new ProgressDialog(MainMenuActivity.this);
                progressBar.setCancelable(true);
                progressBar.setMessage("Loading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBarStatus = 0;

                fileSize = 0;
                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {
                            progressBarStatus = downloadFile();

                            try {
                                Thread.sleep(1000);
                            }

                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            progressBarbHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        if (progressBarStatus >= 100) {
                            try {
                                Thread.sleep(2000);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBar.dismiss();

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Δεν είναι δυνατή η σύνδεση με τον διακοσμητή.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            });
                        }
                    }
                }).start();
                return true;
            case R.id.nable:
                intent = new Intent(MainMenuActivity.this, NableAMEAActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
