package com.aspprothes.myportfolio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.aspprothes.myportfolio.networkconnection.Common;

public class HomeActivity extends AppCompatActivity {
    private SwipeRefreshLayout swiperefreshLayout;
    private WebView webView;
    private LinearLayout no_Internet;
    private NetworkConnectionCheck networkConnectionCheck = new NetworkConnectionCheck();
    private LottieAnimationView progress_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getColor(R.color.colors1));
        this.getWindow().setNavigationBarColor(getColor(R.color.colors1));
        setContentView(R.layout.home_activity);

        swiperefreshLayout = findViewById(R.id.swiperefreshLayout);
        webView = findViewById(R.id.webView);
        no_Internet = findViewById(R.id.no_Internet);
        progress_loading = findViewById(R.id.progress_loading);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress_loading.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress_loading.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(getString(R.string.web_url));

        swiperefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progress_loading.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        swiperefreshLayout.setRefreshing(false);
                        progress_loading.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    public class NetworkConnectionCheck extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Common.isNetworkAccess(context)){
                no_Internet.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }else{
                no_Internet.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.reload();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkConnectionCheck,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkConnectionCheck);
    }

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        if (webView.canGoBack() && isTaskRoot()){
            webView.goBack();
        } else if (isTaskRoot()) {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("Warning !!")
                    .setMessage("Do you want to exit this app ?")
                    .setIcon(getDrawable(R.drawable.alert))
                    .setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getColor(R.color.colors1));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getColor(R.color.colors1));
        } else{
            super.onBackPressed();
        }
    }
}