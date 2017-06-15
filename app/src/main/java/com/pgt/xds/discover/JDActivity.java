package com.pgt.xds.discover;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pgt.xds.base.BaseActivity;
import com.pgt.xds.R;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 京东商城网页
 */
public class JDActivity extends BaseActivity {


    @BindView(R.id.jd_web)
    WebView jdWeb;
    @BindView(R.id.myProgressBar)
    ProgressBar progressBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jd;
    }

    @Override
    protected void initView() {
        //设置支持js脚本
        jdWeb.getSettings().setJavaScriptEnabled(true);
        jdWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        jdWeb.getSettings().setDomStorageEnabled(true);
        //设置进度条
        jdWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && !JDActivity.this.isFinishing()) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility() && !JDActivity.this.isFinishing()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        // 覆盖webView默认通过系统或者第三方浏览器打开网页的行为
        // 如果为false调用系统或者第三方浏览器打开网页的行为
        jdWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // webView加载web资源

                if (shouldOverrideUrlLoadingByApp(view, url)) {
                    return true;
                }
               view.loadUrl(url);
                return  true;
            }
        });
        jdWeb.loadUrl("http://mall.jd.com/index-115878.html");
    }
    /**
     * 根据url的scheme处理跳转第三方app的业务
     */
    private boolean shouldOverrideUrlLoadingByApp(WebView view, String url) {
        if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
            //不处理http, https, ftp的请求
            return false;
        }
        Intent intent;
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            //XLLog.e(TAG, "URISyntaxException: " + e.getLocalizedMessage());
            return false;
        }
        intent.setComponent(null);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
           // XLLog.e(TAG, "ActivityNotFoundException: " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }


    @OnClick(R.id.left_layout)
    public void onViewClicked() {
        finish();
    }
}
