package vcc.viv.ads.demo.bin.demo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsForm;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.WebViewType;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsPopupBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsPopupFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsPopupFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private final String zoneId = "2013985";
    private final String requestId = "1";

    private FragmentDetailAdsPopupBinding binding;

    /*
     * Area : Instance
     */
    public static DetailAdsPopupFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsPopupFragment myFragment = new DetailAdsPopupFragment();
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
        binding = FragmentDetailAdsPopupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolkit.logger.info("variable");
        Context context = getContext();
        Resources resources = context.getResources();

        toolkit.logger.info("register callback with ads manager");
        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                AdsData info = toolkit.adsManager.addAds(AdsForm.popup, binding.root, tag, requestId, adsInfo.get(0).zoneId);
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

        toolkit.logger.info("request for popup");
//        toolkit.adsManager.request(tag, requestId, new AdsRequestWorker.ReaderParameter(
//                Const.Ads.Test.userId,
//                new ArrayList<String>() {{
//                    add(zoneId);
//                }},
//                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
//        ));

        List<WebViewType> formats = new ArrayList();
        formats.add(WebViewType.top);
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