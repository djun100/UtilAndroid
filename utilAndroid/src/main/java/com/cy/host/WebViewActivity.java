package com.cy.host;//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Message;
//import android.webkit.ConsoleMessage;
//import android.webkit.JsPromptResult;
//import android.webkit.JsResult;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.cy.app.Log;
//import com.cy.host.R;
//import com.cy.host.TempConstant;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//
///**
// * Created by Administrator on 2016/11/14.
// * 这是web 页面
// */
//
//public class WebViewActivity extends Activity {
//    public static String Key_Title = "Key_Title";
//    public static String Key_Type = "Key_Type";
//    public static String Key_FromNotifi= "Key_FromNotifi";
//    private WebView webview;
//    private String Url;
//    private int type;
//    private String title;
//    private boolean mIsFromNotification=false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);
//        setLeftButtonOnDefaultClickListen();
//
//        type = getIntent().getIntExtra(Key_Type, -1);
//        title = getIntent().getStringExtra(Key_Title);
//        mIsFromNotification=getIntent().getBooleanExtra(Key_FromNotifi,false);
//        if (mIsFromNotification){
//            TempConstant.gtaskfragment_NeedToRefresh=true;
//        }
//        if (type == 1) {
//            setTitleVis(1);
//        } else {
//            setFormTitle(getIntent().getStringExtra(Key_Title));
//            setHeaderLineVis(1);
//        }
//
//        Url = getIntent().getStringExtra("url");
//        initView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        webview.onResume();
//    }
//
//    private void initView() {
//        webview = (WebView) findViewById(R.id.webview);
//        initWebViewSettings();
//        webview.loadUrl(Url);
//        webview.setWebViewClient(new MyWebViewClient());
//    }
//
//    Timer timer = new Timer();
//    MyTask task;
//
//    class MyWebViewClient extends WebViewClient {
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            Log.w("onPageFinished url "+url);
//            task = new MyTask(url);
//            timer.schedule(task, 1000);
//
//            if (url.contains("appindex.action")) {
//                webview.stopLoading();
//                WebViewActivityBack.this.finish();
//            }
//            super.onPageFinished(view, url);
//
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            Log.w("onPageStarted url "+url);
//            if (task != null) {
//                task.cancel();
//                if (mHasPageInited==false) {
//                    ProgressShow(R.string.dialog_waitdata__tip);
//                }
//            }
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            view.loadUrl(url);
//            if (url.contains("&openWindow=true")) {
//                webview.stopLoading();
//                Intent intent = new Intent(WebViewActivityBack.this, OtherWebViewActivity.class);
//                intent.putExtra("url", url);
//                intent.putExtra(OtherWebViewActivity.Key_Title, "合同详情");
//                base_startActivity(intent);
//                return true;
//            }
//
//            return false;
//        }
//
//    }
//
//    boolean mHasPageInited=false;
//    class MyTask extends TimerTask {
//        String url;
//
//        public MyTask(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public void run() {
//            mHasPageInited=true;
//            ProgressHide();
//            android.util.Log.e("TEST", "this is last page, url:" + url);
//        }
//
//    }
//
//    private void initWebViewSettings() {
//        webview.getSettings().setSupportZoom(true);
//        webview.getSettings().setBuiltInZoomControls(true);
//        webview.getSettings().setDefaultFontSize(12);
//        webview.getSettings().setLoadWithOverviewMode(true);
//        // 设置可以访问文件
//        webview.getSettings().setAllowFileAccess(true);
//        // 如果访问的页面中有Javascript，则webview必须设置支持Javascript
//        webview.getSettings().setJavaScriptEnabled(true);
//        // webview.getSettings().setUserAgentString(MyApplication.getUserAgent());
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webview.getSettings().setAllowFileAccess(true);
//        webview.getSettings().setAppCacheEnabled(true);
//        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings().setDatabaseEnabled(true);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webview.getSettings().setSupportMultipleWindows(true);
//
//        webview.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放。
//
//        webview.setWebChromeClient(new MyWebChromeClient());
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        webview.onPause();
//    }
//
//    public class MyWebChromeClient extends WebChromeClient {
//
//        @Override
//        public void onCloseWindow(WebView view) {
//            if (Build.VERSION.SDK_INT < 20) {
//                if (view != null) {
//                    TempConstant.gtaskfragment_NeedToRefresh=true;
//                    WebViewActivityBack.this.finish();
//                }
//            }
//        }
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean dialog,
//                                      boolean userGesture, Message resultMsg) {
//            return super.onCreateWindow(view, dialog, userGesture, resultMsg);
//
//        }
//
//        /**
//         * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
//         */
//        public boolean onJsAlert(WebView view, String url, String message,
//                                 JsResult result) {
//
//            if (message.equals("loadOver")) {
//                result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
//                return true;
//            } else {
//                return super.onJsAlert(view, url, message, result);
//            }
//
//        }
//
//        public boolean onJsBeforeUnload(WebView view, String url, String message,
//                                        JsResult result) {
//            return super.onJsBeforeUnload(view, url, message, result);
//        }
//
//        /**
//         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
//         */
//        public boolean onJsConfirm(WebView view, String url, String message,
//                                   final JsResult result) {
//
//            return super.onJsConfirm(view, url, message, result);
//        }
//
//        /**
//         * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
//         * window.prompt('请输入您的域名地址', '618119.com');
//         */
//        public boolean onJsPrompt(WebView view, String url, String message,
//                                  String defaultValue, final JsPromptResult result) {
//
//            return super.onJsPrompt(view, url, message, defaultValue,
//                    result);
//        }
//
//        int tempProgress = 0;
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
////            android.util.Log.e("TEST", "进度:" + newProgress);
//            super.onProgressChanged(view, newProgress);
//        }
//
//        @Override
//        public void onReceivedIcon(WebView view, Bitmap icon) {
//            super.onReceivedIcon(view, icon);
//        }
//
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            super.onReceivedTitle(view, title);
//        }
//
//        @Override
//        public void onRequestFocus(WebView view) {
//            super.onRequestFocus(view);
//        }
//
//        @Override
//        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//
//            if (Build.VERSION.SDK_INT > 20) {
//                if (consoleMessage.message().equals("Scripts may close only the windows that were opened by it.")) {
//                    TempConstant.gtaskfragment_NeedToRefresh=true;
//                    WebViewActivityBack.this.finish();
//                    return true;
//                } else {
//                    return super.onConsoleMessage(consoleMessage);
//                }
//
//            }
//            return super.onConsoleMessage(consoleMessage);
//        }
//    }
//
//
//
//}
