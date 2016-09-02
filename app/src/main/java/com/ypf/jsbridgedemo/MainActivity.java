package com.ypf.jsbridgedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button getJS;
    private TextView showJSStr;
    private WebView webView;
    String testStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJS = (Button) findViewById(R.id.getJS_bt);
        showJSStr = (TextView) findViewById(R.id.getJSStr_tv);
        webView = (WebView) findViewById(R.id.webview1);

        getJS.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new mJavascriptInterface(), "elso"); //设置本地调用对象及其接口
        webView.loadUrl("file:///android_asset/test.html");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getJS_bt:
                //Android调用JS
                webView.loadUrl("javascript:sendAndroid()");
                showJSStr.setText(testStr);
                break;

            default:
                break;
        }

    }

    public class mJavascriptInterface {

        //JS调用Android
        @JavascriptInterface
        public String sendJS() {
            return "我来自Android";
        }
        @JavascriptInterface
        public void getJS(String data) {
            testStr = data;
            Log.i("FormJS", testStr);
        }

    }
}
