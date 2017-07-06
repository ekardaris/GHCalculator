package nableamea.ghcalculator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GHDActivity extends Activity {

    private Spinner weightSpinner;
    private Button ghProductButton;
    private TextView ghdDosageTextView;
    private TextView ghdInjectionsTextView;
    private TextView ghdWeekDoseTextView;
    private TextView ghdNoteOne;
    private TextView ghdNoteTwo;
    private TextView ghdNoteThree;
    private TextView cell3HeaderTextView;
    private String[] dosageArray;
    private String[] injectionsArray;


    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghd);

        weightSpinner = (Spinner) findViewById(R.id.weightSpinner);
        ghProductButton = (Button) findViewById(R.id.ghProductButton);
        ghProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(GHDActivity.this, NOMPressActivity.class);
                intent.putExtra("dailyDose", Double.parseDouble(ghdDosageTextView.getText().toString()));
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.gdh_weights, R.layout.spinner_layout); //change the last argument here to your xml above.
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(typeAdapter);

        ghdDosageTextView = (TextView) findViewById(R.id.ghdDosageTextView);
        ghdWeekDoseTextView = (TextView) findViewById(R.id.ghdWeekDoseTextView);

        ghdNoteOne = (TextView) findViewById(R.id.ghd_note_one);
        ghdNoteTwo = (TextView) findViewById(R.id.ghd_note_two);
        ghdNoteThree = (TextView) findViewById(R.id.ghd_note_three);

        SpannableString formattedString = new SpannableString(getString(R.string.ghd_note_one));
        formattedString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ghdNoteOne.setText(formattedString);

        formattedString = new SpannableString(getString(R.string.ghd_note_two));
        formattedString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ghdNoteTwo.setText(formattedString);
        ghdNoteThree.setText(getResources().getString(R.string.ghd_note_three));

        dosageArray = getResources().getStringArray(R.array.gdh_dosages);
        injectionsArray = getResources().getStringArray(R.array.gdh_injections);

        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ghdDosageTextView.setText(dosageArray[position]);
                DecimalFormat formatter = new DecimalFormat("#0.0");
                DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
                sym.setDecimalSeparator('.');
                formatter.setDecimalFormatSymbols(sym);
                ghdWeekDoseTextView.setText("" + formatter.format(Double.parseDouble(dosageArray[position]) * 7.0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        formattedString = new SpannableString("mg/m2 σωματικής\n" +
                "επιφάνειας / ημέρα");
        formattedString.setSpan(new SuperscriptSpan(), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        formattedString.setSpan(new RelativeSizeSpan(0.75f), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        cell3HeaderTextView = (TextView) findViewById(R.id.cell3);
        cell3HeaderTextView.setText(formattedString);
        findViewById(R.id.cell1).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell2).setBackgroundResource(R.drawable.cell_border);
        cell3HeaderTextView.setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell4).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell5).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell6).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell7).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell8).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell9).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell10).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell11).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.cell12).setBackgroundResource(R.drawable.cell_border);

    }

    public int downloadFile() {
        while (fileSize <= 1000000) {
            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            } else if (fileSize == 400000) {
                return 40;
            } else if (fileSize == 500000) {
                return 50;
            } else if (fileSize == 700000) {
                return 70;
            } else if (fileSize == 800000) {
                return 80;
            }
            else if (fileSize == 900000) {
                return 90;
            }
        }
        return 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dosometer_menu, menu);
        return true;
    }

    public boolean ShowMenuOption(MenuItem item) {
        return true; //Indicated menu press was handled
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.main_menu_option:
                intent = new Intent(GHDActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.product_summary_option:
                progressBar = new ProgressDialog(GHDActivity.this);
                progressBar.setCancelable(true);
                progressBar.setMessage("File downloading ...");
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
                            } catch (InterruptedException e) {
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
                            } catch (InterruptedException e) {
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
            case R.id.yellow_card:
                intent = new Intent(GHDActivity.this, YellowCardActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
