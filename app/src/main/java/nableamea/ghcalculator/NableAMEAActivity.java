package nableamea.ghcalculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class NableAMEAActivity extends Activity {

    PopupWindow popupWindow;
    Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nable_amea);

        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) NableAMEAActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        final View layout = inflater.inflate(R.layout.popup,
                (ViewGroup) findViewById(R.id.popup_element));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        // create a 300px width and 470px height PopupWindow
        popupWindow = new PopupWindow(layout, params.width, 700, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
        new Handler().postDelayed(new Runnable() {

            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }

        }, 100L);

        closeButton = (Button) layout.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
        closeButton.setBackgroundResource(R.drawable.button_effect);
    }
}
