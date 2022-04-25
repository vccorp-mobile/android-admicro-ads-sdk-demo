package vcc.viv.ads.demo.bin.demo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.bin.adsenum.WebViewType;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsInpageBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsInPageFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsInPageFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private final String zoneId = "2019570";
    private final String requestId = "1";

    private FragmentDetailAdsInpageBinding binding;

    /*
     * Area : Instance
     */
    public static DetailAdsInPageFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsInPageFragment myFragment = new DetailAdsInPageFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    /*
     * Area : Override
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDetailAdsInpageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolkit.logger.info("variable");
        Context context = getContext();
        Resources resources = context.getResources();

        toolkit.logger.info("view");
        binding.fakeView1.setOnClickListener(v -> Snackbar.make(view, "click fake view 1", Snackbar.LENGTH_SHORT).show());
        binding.fakeView2.setOnClickListener(v -> Snackbar.make(view, "click fake view 2", Snackbar.LENGTH_SHORT).show());
        binding.fakeBlank.setOnTouchListener((v, event) -> {
//            toolkit.adsManager.click(event, tag, requestId, zoneId);
            toolkit.adsManager.click(event, tag, requestId, WebViewType.inPage.toString());
            return true;
        });

        toolkit.logger.info("register callback with ads manager");
        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                AdsData info = toolkit.adsManager.addAds(AdsForm.inPage, binding.adsLayout, tag, requestId, adsInfo.get(0).zoneId);
                if (info != null) {
                    toolkit.logger.debug(toolkit.gson.toJson(info));
                }
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
                toolkit.logger.warning(String.format(Const.Error.error, id + " - " + msg));
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

        toolkit.logger.info("request in page ads");
//        toolkit.adsManager.request(tag, requestId, new AdsRequestWorker.ReaderParameter(
//                Const.Ads.Test.userId, Const.Ads.Test.zoneId,
//                new ArrayList<String>() {{
//                    add(zoneId);
//                }},
//                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
//        ));

        List<WebViewType> formats = new ArrayList();
        formats.add(WebViewType.inPage);
        toolkit.adsManager.request(tag, requestId, formats);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolkit.adsManager.callbackUnregister(tag);
    }

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
}