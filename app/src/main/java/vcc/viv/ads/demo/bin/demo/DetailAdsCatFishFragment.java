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
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsCatfishBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsCatFishFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsCatFishFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private final String zoneId = "2019566";
    private final String requestId = "1";

    private FragmentDetailAdsCatfishBinding binding;

    /*
     * Area : Instance
     */
    public static DetailAdsCatFishFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsCatFishFragment myFragment = new DetailAdsCatFishFragment();
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
        binding = FragmentDetailAdsCatfishBinding.inflate(inflater, container, false);
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

            @Override
            public void closeWebViewAds(String id, String requestId, String zoneId) {
                super.closeWebViewAds(id, requestId, zoneId);
                if (!tag.equals(id)) return;

                toolkit.adsManager.close(tag, requestId, zoneId, binding.root);
            }

            @Override
            public void openWebAds(String id, String requestId, String zoneId, String url) {
                super.openWebAds(id, requestId, zoneId, url);
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

        toolkit.logger.info("request for catfish");
        AdsRequest.ReaderParameter requestParameter = new AdsRequest.ReaderParameter(
                Const.Ads.Test.userId,
                new ArrayList<Zone>() {{
                    add(new Zone(zoneId, Zone.AdsType.catfish));
                }},
                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
        );
        toolkit.adsManager.request(tag, requestId, requestParameter);
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