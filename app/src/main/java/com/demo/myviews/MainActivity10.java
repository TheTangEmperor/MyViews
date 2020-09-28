package com.demo.myviews;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity10 extends AppCompatActivity implements View.OnClickListener {


    WebView mWebView;
    Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        mWebView = (WebView) findViewById(R.id.wbView)    ;
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);


//设置WebView属性，能够执行Javascript脚本
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //webview.setWebViewClient(new HelloWebViewClient());
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.setWebChromeClient(new MyChromeWebClient());


        webSettings.setDatabaseEnabled(true);// 数据库缓存
        webSettings.setAppCacheEnabled(true);// 打开缓存
        webSettings.setDomStorageEnabled(true);// 打开dom storage缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);// 提高渲染级别
        webSettings.setLoadsImagesAutomatically(true);// 自动加载网络图片
        webSettings.setDefaultTextEncodingName("utf-8");// 设置默认编码方式
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSettings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(true);

//        设置http和https链接都可用
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String ua = webSettings.getUserAgentString();
        Log.d(TAG, "onCreate: " + ua);
        webSettings.setUserAgentString(ua + " MicroMessenger/4.5.255");
        Log.d(TAG, "onCreate: " + webSettings.getUserAgentString());

//        DYJsInterface click = new DYJsInterface(this);
        //这里添加JS的交互事件，这样H5就可以调用原生的代码
//        mWebView.addJavascriptInterface(click, click.toString());
        handleSSLHandshake();

    }

    @Override
    public void onClick(View v) {
        mWebView.loadUrl("https://wxpay.wxutil.com/mch/pay/h5.v2.php");
//        mWebView.loadUrl("http://shengtai.polms.cn/index.php/bzcsnew/index/index/t/7/p/6");
    }


    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }



    private static final String TAG = "MainActivity10";

    /**
     * 监听开始加载和加载完成
     */
    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG,"shouldOverrideUrlLoading: " + url);

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG,"onPageStarted: " + url);
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG,"onPageFinished: " + url);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d(TAG,"onReceivedError: " + error);
        }
    }
}