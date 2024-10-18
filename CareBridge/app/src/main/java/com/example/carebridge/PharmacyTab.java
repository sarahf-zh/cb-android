package com.example.carebridge;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;


public class PharmacyTab extends Fragment {

    private static final String TAG = "PharmacyTab";
    private boolean reload;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pharmacy, container, false);
        WebView webView = (WebView)rootView.findViewById(R.id.pharmacy);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        reload = false;
        webView.setWebViewClient(new WebViewClient() {
             public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                 Log.e(TAG, "Webview error " + error.getErrorCode() + " ---> " + error.getDescription());
                 if (!reload) {
                     webView.reload();
                     reload = true;
                 }
             }
         });

        webView.loadUrl("https://www.healthwarehouse.com");
        //webView.loadUrl("https://www.alldaychemist.com");
        //webView.loadUrl("https://pharmacy.amazon.com");
        //webView.loadUrl("https://www.goodrx.com/");
        return rootView;
    }

}

