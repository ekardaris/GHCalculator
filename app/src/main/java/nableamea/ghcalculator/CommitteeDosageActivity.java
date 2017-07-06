package nableamea.ghcalculator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.style.SuperscriptSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CommitteeDosageActivity extends Activity {

    private TextView confirmedDailyDoseTextView;
    private EditText confirmedWeekDosageEditText;
    private Button committeeProductButton;
    private TextView committeeNoteZero;
    private TextView committeeNoteOne;
    private TextView committeeNoteTwo;
    private TextView committeeNoteThree;
    private TextView committeeNoteFour;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_dosage);

        confirmedDailyDoseTextView = (TextView) findViewById(R.id.confirmedDailyDoseTextView);
        confirmedWeekDosageEditText = (EditText) findViewById(R.id.confirmedWeekDosageEditText);

        confirmedWeekDosageEditText.setKeyListener(DigitsKeyListener.getInstance(false, true));
        confirmedWeekDosageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (confirmedWeekDosageEditText.getText().length() > 0) {
                    DecimalFormat df;
                    Locale locale = Locale.getDefault();
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
                    symbols.setDecimalSeparator('.');
                    df = new DecimalFormat("0.00", symbols);

                    confirmedDailyDoseTextView.setText("" + df.format(Double.parseDouble(confirmedWeekDosageEditText.getText().toString()) / 7.0));
                } else {
                    confirmedDailyDoseTextView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmedWeekDosageEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_GO) {

                    if (confirmedDailyDoseTextView.getText().length() == 0) {

                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (imm != null) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }

                        Toast toast = Toast.makeText(CommitteeDosageActivity.this, "Παρακαλώ εισάγετε εβδομαδιαία δόση", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Intent intent;
                        intent = new Intent(CommitteeDosageActivity.this, NOMPressActivity.class);
                        intent.putExtra("dailyDose", Double.parseDouble(confirmedDailyDoseTextView.getText().toString()));
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        committeeProductButton = (Button) findViewById(R.id.committeeProductButton);
        committeeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmedDailyDoseTextView.getText().length() == 0) {
                    Toast toast = Toast.makeText(CommitteeDosageActivity.this, "Παρακαλώ εισάγετε εβδομαδιαία δόση", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent intent;
                    intent = new Intent(CommitteeDosageActivity.this, NOMPressActivity.class);
                    intent.putExtra("dailyDose", Double.parseDouble(confirmedDailyDoseTextView.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        committeeNoteZero = (TextView) findViewById(R.id.committee_note_zero);
        committeeNoteZero.setText(getString(R.string.committee_note_zero));

        committeeNoteOne = (TextView) findViewById(R.id.committee_note_one);
        committeeNoteTwo = (TextView) findViewById(R.id.committee_note_two);
        committeeNoteThree = (TextView) findViewById(R.id.committee_note_three);

        SpannableString formattedString = new SpannableString(getString(R.string.ghd_note_one));
        formattedString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        committeeNoteOne.setText(formattedString);

        formattedString = new SpannableString(getString(R.string.ghd_note_two));
        formattedString.setSpan(new SuperscriptSpan(), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        committeeNoteTwo.setText(formattedString);
        committeeNoteThree.setText(getResources().getString(R.string.ghd_note_three));

        committeeNoteFour = (TextView) findViewById(R.id.committee_note_four);
        committeeNoteFour.setText(getString(R.string.turner_note));
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
                intent = new Intent(CommitteeDosageActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.product_summary_option:
                progressBar = new ProgressDialog(CommitteeDosageActivity.this);
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
                intent = new Intent(CommitteeDosageActivity.this, YellowCardActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
