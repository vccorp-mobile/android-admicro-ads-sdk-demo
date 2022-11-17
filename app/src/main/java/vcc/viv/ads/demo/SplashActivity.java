package vcc.viv.ads.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsColor;
import vcc.viv.ads.bin.AdsImage;
import vcc.viv.ads.bin.AdsLogger;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequestParam;
import vcc.viv.ads.bin.AdsTime;
import vcc.viv.ads.bin.AdsWelcome;
import vcc.viv.ads.bin.InitializeParameter;
import vcc.viv.ads.bin.adsenum.AdsBrowser;
import vcc.viv.ads.demo.databinding.SplashActivityBinding;
import vcc.viv.ads.demo.util.Const;

public class SplashActivity extends AppCompatActivity {
    private static String tag = SplashActivity.class.getSimpleName();
    private SplashActivityBinding binding;
    private AdsManager adsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        binding = SplashActivityBinding.inflate(inflater);
        setContentView(binding.getRoot());
        adsManager = AdsManager.getInstance();

        //khai bao ads manager
        List<String> positions = new ArrayList<String>() {{
            add("1");
        }};
        AdsRequestParam adsRequestParam = new AdsRequestParam("", "2013893", "1", "1", "1", positions);
        AdsImage adsImage = new AdsImage(R.drawable.ic_title_app, R.drawable.ic_logo_app);
        AdsTime adsTime = new AdsTime(1000, 1000);
        AdsColor adsColor = new AdsColor(R.color.teal_200, R.color.black, false);
        InitializeParameter parameter = new InitializeParameter()
                .setCore(this, Const.appId, BuildConfig.VERSION_CODE + "")                    // require
                .setWelcomeAds(new AdsWelcome(adsImage, adsColor, adsTime, adsRequestParam));
        adsManager.initialize(parameter);

        // đăng ký callback và nhận callback
        AdsManagerCallback adsManagerCallback = new AdsManagerCallback() {
            @Override
            public void closeWelcomeAds(String s, String s1, String s2) {
                super.closeWelcomeAds(s, s1, s2);
                finish();
                MainActivity.start(getApplication());
            }

            @Override
            public void welcomeOpenLink(String s, String s1, String s2, String s3) {
                super.welcomeOpenLink(s, s1, s2, s3);
                finish();
                MainActivity.start(getApplication());
            }
        };
        adsManager.callbackRegister(tag, adsManagerCallback);
    }
}
