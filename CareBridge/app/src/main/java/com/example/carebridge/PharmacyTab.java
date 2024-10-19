package com.example.carebridge;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
import android.widget.ImageButton;


public class PharmacyTab extends Fragment {

    private static final String TAG = "PharmacyTab";
    private boolean reload;
    private WebView webView;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        context = getContext();

        ImageButton backButton = ((MainActivity)getActivity()).getBackButton();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        ImageButton menuButton = ((MainActivity)getActivity()).getMenuButton();
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, menuButton);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int menuId = item.getItemId();
                        if (menuId == R.id.healthwarehouse) {
                            webView.loadUrl("https://www.healthwarehouse.com");
                        } else if (menuId == R.id.alldaychemist) {
                            webView.loadUrl("https://www.alldaychemist.com");
                        } else if (menuId == R.id.amazon) {
                            webView.loadUrl("https://pharmacy.amazon.com");
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_pharmacy, container, false);
        webView = (WebView)rootView.findViewById(R.id.pharmacy);

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
        return rootView;
    }

}

