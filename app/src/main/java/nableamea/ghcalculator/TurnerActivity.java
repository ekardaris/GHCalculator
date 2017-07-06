package nableamea.ghcalculator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.Html;
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

public class TurnerActivity extends Activity {

    private Spinner turnerWeightSpinner;
    private Button turnerProductButton;
    private TextView turnerNoteTextView;
    private TextView turnerDosageTextView;
    private TextView turnerWeekDoseTextView;
    private TextView cell3HeaderTextView;
    private String [] dosageArray;
    private String [] injectionArray;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turner);

        dosageArray = getResources().getStringArray(R.array.turner_dosage);
        injectionArray = getResources().getStringArray(R.array.turner_injections);

        turnerProductButton = (Button) findViewById(R.id.turnerProductButton);
        turnerProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(TurnerActivity.this, NOMPressActivity.class);
                intent.putExtra("dailyDose",Double.parseDouble(turnerDosageTextView.getText().toString()));
                startActivity(intent);
            }
        });

        turnerDosageTextView = (TextView) findViewById(R.id.turnerDosageTextView);
        turnerWeekDoseTextView = (TextView) findViewById(R.id.turnerWeekDoseTextView);

        turnerWeightSpinner = (Spinner) findViewById(R.id.turnerWeightSpinner);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.turner_weight, R.layout.spinner_layout); //change the last argument here to your xml above.
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        turnerWeightSpinner.setAdapter(typeAdapter);

        turnerWeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                turnerDosageTextView.setText(dosageArray[position]);
                DecimalFormat formatter = new DecimalFormat("#0.0");
                DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
                sym.setDecimalSeparator('.');
                formatter.setDecimalFormatSymbols(sym);
                turnerWeekDoseTextView.setText(""+formatter.format(Double.parseDouble(dosageArray[position])*7.0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        turnerNoteTextView = (TextView) findViewById(R.id.turnerNoteTextView);
        turnerNoteTextView.setText(getString(R.string.turner_note));

        cell3HeaderTextView = (TextView) findViewById(R.id.turnerCell3);
        cell3HeaderTextView.setText(Html.fromHtml("mg/m<sup>2</sup> σωματικής\nεπιφάνειας / ημέρα"));


        findViewById(R.id.turnerCell1).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell2).setBackgroundResource(R.drawable.cell_border);
        cell3HeaderTextView.setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell4).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell5).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell6).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell7).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell8).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.turnerCell9).setBackgroundResource(R.drawable.cell_border);
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
                intent = new Intent(TurnerActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.product_summary_option:
                progressBar = new ProgressDialog(TurnerActivity.this);
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
            case R.id.yellow_card:
                intent = new Intent(TurnerActivity.this, YellowCardActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
