package vcc.viv.ads.demo.test.request;

import android.os.Bundle;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.WebViewType;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;

public class AdsTestRequestActivity extends BaseActivity {
    /*
     * Area : Variable
     */
    private final String tag = AdsTestRequestActivity.class.getSimpleName();
    private final List<WebViewType> zoneIds = new ArrayList() {{
        add(WebViewType.medium);

//        add(WebViewType.TOP);
//        add(WebViewType.BILLBOARD);
//        add(WebViewType.MEDIUM);
//        add(WebViewType.ADX_NATIVE);
//        add(WebViewType.ADX_SPONSOR_180200);
//        add(WebViewType.ADX_SPONSOR_300250);
//        add(WebViewType.LOTUS_SHOP_CHAT);
//        add(WebViewType.IPOSTER);
//        add(WebViewType.INPAGE);
//        add(WebViewType.NATIVE);
//        add(WebViewType.NATIVE_VIDEO);
//        add(WebViewType.PLAYER_LIVE);
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
                toolkit.adsManager.request(tag, requestId, zoneIds);
            }

            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                ConstraintLayout constraintLayout = findViewById(R.id.root);
                AdsData info = toolkit.adsManager.addAds(constraintLayout, tag, requestId, adsInfo.get(0).zoneId);
                Log.d(tag, new Gson().toJson(info));
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);
    }

    /*
     * Area : Inner Class
     */
}