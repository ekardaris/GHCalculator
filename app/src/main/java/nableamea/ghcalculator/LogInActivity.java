package nableamea.ghcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button logInButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = (Button) findViewById(R.id.logInButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        usernameEditText.setBackgroundResource(android.R.drawable.edit_text);

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    usernameEditText.setBackgroundResource(R.drawable.edit_text_border);
                } else {
                    usernameEditText.setBackgroundResource(android.R.drawable.edit_text);
                }
            }
        });

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.setBackgroundResource(android.R.drawable.edit_text);

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordEditText.setBackgroundResource(R.drawable.edit_text_border);
                } else {
                    passwordEditText.setBackgroundResource(android.R.drawable.edit_text);
                }
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameEditText.setText("");
                passwordEditText.setText("");
            }
        });

        logInButton.setBackgroundResource(R.drawable.button_effect);
        clearButton.setBackgroundResource(R.drawable.button_effect);
    }
}
