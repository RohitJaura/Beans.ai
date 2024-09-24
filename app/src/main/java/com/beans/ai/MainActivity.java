package com.beans.ai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beans.ai.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("webViewTitle");
        binding.tvWebVTitle.setText(title != null ? title : "Web View Title");
        String url = getIntent().getStringExtra("webViewUrl");
        if (url == null || url.isEmpty()) {
            url = "file:///android_asset/form.html";
        }

        //listeners
        binding.fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //web view
        binding.wvWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
        binding.wvWebView.getSettings().setJavaScriptEnabled(true);
        binding.wvWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        binding.wvWebView.loadUrl(url);

        //closure
    }

    //
    @Override
    public void onBackPressed() {
        if (binding.wvWebView.canGoBack()) {
            binding.wvWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //
    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void saveEmployeeData(String jsonData) {
            //
            SharedPreferences prefs = getSharedPreferences("EmployeeData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("employeeDetails", jsonData);
            editor.apply();

            //
            displayCachedData();
        }
    }

    //
    private void displayCachedData() {
        SharedPreferences prefs = getSharedPreferences("EmployeeData", MODE_PRIVATE);
        String employeeDetails = prefs.getString("employeeDetails", null);

        if (employeeDetails != null) {
            try {
                JSONObject employeeData = new JSONObject(employeeDetails);
                Intent intent = new Intent(MainActivity.this, NativeActivity.class);
                intent.putExtra("firstname", employeeData.getString("firstname"));
                intent.putExtra("lastname", employeeData.getString("lastname"));
                intent.putExtra("gender", employeeData.getString("gender"));
                intent.putExtra("empid", employeeData.getString("empid"));
                intent.putExtra("designation", employeeData.getString("designation"));
                intent.putExtra("phno", employeeData.getString("phno"));
                startActivity(intent);
            } catch (JSONException e) {
                Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}