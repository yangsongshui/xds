package com.pgt.xds.discover;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pgt.xds.BaseActivity;
import com.pgt.xds.R;

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
                view.loadUrl(url);
                return true;
            }
        });
        jdWeb.loadUrl("https://mall.jd.com/index-115878.html");
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
