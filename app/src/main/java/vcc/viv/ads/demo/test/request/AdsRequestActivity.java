package vcc.viv.ads.demo.test.request;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;
import vcc.viv.ads.demo.util.Const;

public class AdsRequestActivity extends BaseActivity {
    /*
     * Area : Variable
     */
    private final String tag = AdsRequestActivity.class.getSimpleName();
    private final List<String> zoneIds = new ArrayList<String>() {{
//        add("2016084"); // current test
//
//        add("2016084"); // native

        add("2016694"); // webview
//        add("2013986");
//        add("2019570");
//        add("2013985");
//        add("2013635");
//        add("2014136");
//        add("2014137");
    }};
    private final String requestId = "1";

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_request);
        toolkit.initAdsSdk(this);
        initSdkCallback();
    }

    /*
     * Area : Function
     */
    private void initSdkCallback() {
        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void initSuccess() {
                super.initSuccess();
                AdsRequest.ReaderParameter requestParameter = new AdsRequest.ReaderParameter(
                        Const.Ads.Test.userId,
                        new ArrayList<String>() {{
                            add(zoneIds.get(0));
                        }},
                        Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
                );
                toolkit.adsManager.request(tag, requestId, requestParameter);
            }

            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;

                runOnUiThread(() -> {
                    ConstraintLayout constraintLayout = findViewById(R.id.root);AdsData info = toolkit.adsManager.addAds(constraintLayout, tag, requestId, adsInfo.get(0).zoneId);
                    Log.d(tag, new Gson().toJson(info));
                });

                new Handler().postDelayed(() -> toolkit.adsManager.visibility(tag, requestId, adsInfo.get(0).zoneId, true, 0.5), 2000);
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);
    }

    /*
     * Area : Inner Class
     */
}