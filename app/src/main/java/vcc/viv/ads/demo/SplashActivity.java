package vcc.viv.ads.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.AdsWelcome;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.demo.bin.BaseActivity;
import vcc.viv.ads.demo.databinding.ActivitySplashBinding;
import vcc.viv.ads.demo.util.Const;

public class SplashActivity extends BaseActivity {
    private static final String tag = SplashActivity.class.getSimpleName();
    ActivitySplashBinding binding;
    private final String zoneId = "2027146";
    private final String requestId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Context context = getApplication();
        toolkit.initAdsSdk(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        binding = ActivitySplashBinding.inflate(inflater);
        setContentView(binding.getRoot());

        AdsWelcome adsWelcome = new AdsWelcome(R.drawable.dummy_gif, R.drawable.dummy_gif_cat2, 5000);
        toolkit.adsManager.setWelcomeAds(adsWelcome);

        AdsManagerCallback callback = new AdsManagerCallback() {

            @Override
            public void initSuccess() {
                super.initSuccess();
                AdsRequest.ReaderParameter requestParameter = new AdsRequest.ReaderParameter(
                        Const.Ads.Test.userId,
                        new ArrayList<Zone>() {{
                            add(new Zone(zoneId, Zone.AdsType.welcome));
                        }},
                        Const.Ads.Test.positions, "https://app.kenh14.vn/home", "https://app.kenh14.vn/home", 1, 1
                );
                toolkit.adsManager.request(tag, requestId, requestParameter);
            }

            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                toolkit.adsManager.addAds(AdsForm.welcome, binding.root, tag, requestId, adsInfo.get(0).zoneId, null, null);
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
            }

            @Override
            public void closeWebViewAdsSuccess(String s, String s1, String s2) {
                super.closeWebViewAdsSuccess(s, s1, s2);
                finish();
                MainActivity.start(context);
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

    }
}