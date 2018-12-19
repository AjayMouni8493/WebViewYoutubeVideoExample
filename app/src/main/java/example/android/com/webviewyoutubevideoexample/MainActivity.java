package example.android.com.webviewyoutubevideoexample;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.io.IOException;

public class MainActivity extends YouTubeBaseActivity implements View.OnClickListener {

    public static String YOUTUBE_KEY = "AIzaSyDBEvWD_rDVAqPTomIgMqEyDKASJz3nAR4";

    private WebView mWebView;
    private String youtubeURL ="https://www.youtube.com/embed/";
    private String youTubeID ="_0c0ay1570g";
    private ImageView btnBack, btnPlayPause, btnForward;
    private boolean isPlay = false;
    private int video_start_time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWebViewSettings();
        btnBack = (ImageView)findViewById(R.id.btn_backward);
        btnPlayPause = (ImageView)findViewById(R.id.btn_play_pause);
        btnForward = (ImageView)findViewById(R.id.btn_forward);
        btnBack.setOnClickListener(this);
        btnPlayPause.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        mWebView.loadUrl(youtubeURL+youTubeID);
        // mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isPlay = true;
                btnPlayPause.setImageResource(R.drawable.ic_pause_black_32dp);
                playVideo();
            }
        });

    }


    private void playVideo() {
        mWebView.onResume();
        playEnable();
        isPlay = true;
        btnPlayPause.setImageResource(R.drawable.ic_pause_black_32dp);
    }

    private void setPause() {
        mWebView.onPause();
        isPlay = false;
        btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_32dp);
    }

    private void setWebViewSettings() {

        try {

            mWebView = (WebView)findViewById(R.id.web_videoPlayer);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setUserAgentString("0");
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.setKeepScreenOn(true);
            mWebView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void playEnable() {

        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = mWebView.getLeft() + mWebView.getWidth()/2;
        float y = mWebView.getTop() + mWebView.getHeight()/2;
        final MotionEvent downEvent = MotionEvent.obtain( downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0 );
        final MotionEvent upEvent = MotionEvent.obtain( downTime, downTime+ delta, MotionEvent.ACTION_UP, x+10, y+10, 0 );

        mWebView.post(new Runnable() {
            @Override
            public void run() {
                if (mWebView != null) {
                    mWebView.dispatchTouchEvent(downEvent);
                    mWebView.dispatchTouchEvent(upEvent);
                }
            }
        });
    }



    private void playDisable(final WebView webview) {

        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = webview.getLeft() + webview.getWidth()/2;
        float y = webview.getTop() + webview.getHeight()/2;
        final MotionEvent downEvent = MotionEvent.obtain( downTime, downTime + delta, MotionEvent.ACTION_DOWN, x+10, y+10, 0 );
        final MotionEvent upEvent = MotionEvent.obtain( downTime, downTime+ delta, MotionEvent.ACTION_UP, x, y, 0 );

        webview.post(new Runnable() {
            @Override
            public void run() {
                if (webview != null) {
                    webview.dispatchTouchEvent(downEvent);
                    webview.dispatchTouchEvent(upEvent);
                }
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            mWebView.onPause();
            mWebView.pauseTimers();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mWebView.resumeTimers();
            mWebView.onResume();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        try {
            mWebView.destroy();
            mWebView = null;
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_backward:
                System.out.println("######################################################## Backward");
                if (mWebView.canGoBack()){
                    mWebView.goBack();
                }
                /*if (video_start_time>=30){
                    video_start_time = video_start_time-30;
                }
                if (video_start_time!=0){
                    mWebView.loadUrl(youtubeURL+youTubeID+"?"+video_start_time);
                    setAutoPlay();
                }*/
                break;

            case R.id.btn_play_pause:
                if (!isPlay){
                    playVideo();
                }

                else {
                    setPause();
                }
                System.out.println("######################################################## Play ");
                break;

            case R.id.btn_forward:
                System.out.println("######################################################## Forward");
                if (mWebView.canGoForward()){
                    mWebView.goForward();
                }
               /* if (video_start_time>=0){
                    video_start_time = video_start_time+30;
                }
                mWebView.loadUrl(youtubeURL+youTubeID+"?"+video_start_time);
                setAutoPlay();
*/
                break;

        }
    }

}
