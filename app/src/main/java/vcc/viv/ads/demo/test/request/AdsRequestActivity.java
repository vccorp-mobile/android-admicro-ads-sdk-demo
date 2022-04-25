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
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;
import vcc.viv.ads.demo.util.Const;

public class AdsRequestActivity extends BaseActivity {
    /*
     * Area : Variable
     */
    private final String tag = AdsRequestActivity.class.getSimpleName();
    private final List<String> zoneIds = new ArrayList<String>() {{
        add("2019570"); // current test

        add("2019568"); // native

        add("2019569"); // webview
        add("2019570");
        add("2019571");
        add("2019572");
        add("2019573");
        add("2019574");
        add("2019577");
    }};
    private final String requestId = "1";

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_request);
        initSdkCallback();
        toolkit.initAdsSdk(this);

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
                        new ArrayList<Zone>() {{
                            add(new Zone(zoneIds.get(0), Zone.AdsType.popup));
                        }},
                        Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
                );
                toolkit.adsManager.request(tag, requestId, requestParameter);
            }

            @Override
            public void initLocalSuccess() {
                super.initLocalSuccess();
            }

            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;

                runOnUiThread(() -> {
                    ConstraintLayout constraintLayout = findViewById(R.id.root);
                    AdsData info = toolkit.adsManager.addAds(AdsForm.popup, constraintLayout, tag, requestId, adsInfo.get(0).zoneId);
                    Log.d(tag, new Gson().toJson(info));
                });

                new Handler().postDelayed(() -> toolkit.adsManager.visibility(true, 0.5, tag, requestId, adsInfo.get(0).zoneId), 2000);
            }


            @Override
            public void closeWebViewAds(String id, String requestId, String zoneId) {
                super.closeWebViewAds(id, requestId, zoneId);
                if (!tag.equals(id)) return;

                runOnUiThread(() -> {
                    ConstraintLayout constraintLayout = findViewById(R.id.root);
                    toolkit.adsManager.close(tag, requestId, zoneId, constraintLayout);
                    AdsData info = toolkit.adsManager.addAds(constraintLayout, tag, requestId, zoneId);
                });
            }

            @Override
            public void openWebAds(String id, String requestId, String zoneId, String url) {
                super.openWebAds(id, requestId, zoneId, url);
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);
    }

    /*
     * Area : Inner Class
     */
}