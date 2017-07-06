package nableamea.ghcalculator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NOMPressActivity extends AppCompatActivity {

    private double dailyDose;
    private int tabInUse;
    private String[] productColumn;
    private String[] companyColumn;
    private String[] mgPerStrengthColumn;
    private String[] pricePerMgColumn;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nompress);

        dailyDose = getIntent().getExtras().getDouble("dailyDose");
        tabInUse = getIntent().getExtras().getInt("tabInUse");

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.menulogo);

        findViewById(R.id.product_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.company_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.mg_strength_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.price_mg_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.six_month_dose_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.six_month_cost_cell).setBackgroundResource(R.drawable.cell_border);
        findViewById(R.id.week_mg_cell).setBackgroundResource(R.drawable.cell_border);

        TableLayout table = (TableLayout) findViewById(R.id.products_table);
        productColumn = getResources().getStringArray(R.array.products_column);
        companyColumn = getResources().getStringArray(R.array.company_column);
        mgPerStrengthColumn = getResources().getStringArray(R.array.mg_strength_column);
        pricePerMgColumn = getResources().getStringArray(R.array.price_per_mg_column);

        TableRow tableRow;
        TextView textView;

        for (int i = 0; i < productColumn.length; i++) {

            tableRow = new TableRow(this);
            tableRow.setBackgroundResource(R.drawable.line);

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText(productColumn[i]);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.6f));
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText(companyColumn[i]);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2.2f));
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText(mgPerStrengthColumn[i]);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.2f));
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText(pricePerMgColumn[i]);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(textView);

            double sixMonthInjections;
            sixMonthInjections = (186.0 * dailyDose) / Double.parseDouble(mgPerStrengthColumn[i]);
            sixMonthInjections = Math.ceil(sixMonthInjections);

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText("" + (int) sixMonthInjections);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(getResources().getColor(R.color.sandoz));
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(textView);

            Double sixMonthMgCost;
            sixMonthMgCost = sixMonthInjections * Double.parseDouble(pricePerMgColumn[i]) * Double.parseDouble(mgPerStrengthColumn[i]);

            NumberFormat formatter = new DecimalFormat("#0.00");

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText("" + formatter.format(sixMonthMgCost));
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(textView);

            Double weekMgDose;
            weekMgDose = 7 * dailyDose;

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setText("" + weekMgDose);
            textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            textView.setVisibility(View.GONE);
            tableRow.addView(textView);

            table.addView(tableRow);
        }
    }

    public int downloadFile() {
        while (fileSize <= 100000) {
            fileSize++;

            if (fileSize == 10000) {
                return 10;
            } else if (fileSize == 20000) {
                return 20;
            } else if (fileSize == 30000) {
                return 30;
            } else if (fileSize == 40000) {
                return 40;
            } else if (fileSize == 50000) {
                return 50;
            } else if (fileSize == 70000) {
                return 70;
            } else if (fileSize == 80000) {
                return 80;
            }
            else if (fileSize == 90000) {
                return 90;
            }
        }
        return 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nom_press_menu, menu);
        return true;
    }

    public boolean ShowMenuOption(MenuItem item) {
        return true; //Indicated menu press was handled
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.back:
                onBackPressed();
                return true;
            case R.id.update_list:
                progressBar = new ProgressDialog(NOMPressActivity.this);
                progressBar.setCancelable(false);
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
                                Thread.sleep(200);
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
                                progressBar.dismiss();

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Δεν υπάρχει νέα ενημέρωση", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
