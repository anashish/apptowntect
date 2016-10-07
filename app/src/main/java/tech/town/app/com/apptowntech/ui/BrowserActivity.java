package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.utils.Apputil;

public class BrowserActivity extends BaseActivity {

    private WebView mWebview;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        pd = Apputil.createProgressDialog(BrowserActivity.this);
        mWebview = (WebView) findViewById(R.id.webview_browswer);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);

        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);


        mWebview.setWebViewClient(new WebViewClient() {
                                      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                          Toast.makeText(BrowserActivity.this, description, Toast.LENGTH_SHORT).show();
                                      }

                                      @Override
                                      public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                          pd.show();
                                      }


                                      @Override
                                      public void onPageFinished(WebView view, String url) {
                                          pd.dismiss();

                                          String webUrl = mWebview.getUrl();

                                      }

                                  }
        );

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            toolbar.setTitle(bundle.getString("title"));
            mWebview.loadUrl(bundle.getString("url"));
        }

        setToolBarBackButton(toolbar);

    }
}
