package nableamea.ghcalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class YellowCardActivity extends Activity {

    WebView yellowCardWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_card);

        yellowCardWebView = (WebView) findViewById(R.id.yellowCardWebView);
        yellowCardWebView.getSettings().setJavaScriptEnabled(true);
        yellowCardWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        yellowCardWebView.setWebChromeClient(new WebChromeClient());
        yellowCardWebView.getSettings().setBuiltInZoomControls(true);
        yellowCardWebView.getSettings().setSupportZoom(true);
        yellowCardWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        yellowCardWebView.getSettings().setAllowFileAccess(true);
        yellowCardWebView.getSettings().setDomStorageEnabled(true);
        yellowCardWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=www.eof.gr/assets/KITRINI_KARTA.pdf");
    }
}
