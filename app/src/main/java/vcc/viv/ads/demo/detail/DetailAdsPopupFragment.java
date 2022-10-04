package vcc.viv.ads.demo.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsPopupBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsPopupFragment extends Fragment {
    private static final String tag = DetailAdsPopupFragment.class.getSimpleName();

    private Toolkit toolkit = new Toolkit();

    private final String zoneId = "2026668";
    private final String requestId = "1";

    private FragmentDetailAdsPopupBinding binding;

    public static DetailAdsPopupFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsPopupFragment myFragment = new DetailAdsPopupFragment();
        myFragment.setArguments(args);
        return myFragment;
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

        Bundle args = getArguments();
        if (args == null) return;
        String type = args.getString("type", "");
        String link = args.getString("link", "");

        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                AdsData info = toolkit.adsManager.addAds(AdsForm.popup, binding.root, tag, requestId, adsInfo.get(0).zoneId, type, link);
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
            }

            @Override
            public void closeWebViewAdsSuccess(String id, String requestId, String msg) {
                super.closeWebViewAdsSuccess(id, requestId, msg);
            }

            @Override
            public void loadAdsFinish(String id, String requestId, String zoneId) {
                super.loadAdsFinish(id, requestId, zoneId);
            }

            @Override
            public void openWebAds(String id, String requestId, String zoneId, String url,String type) {
                super.openWebAds(id, requestId, zoneId, url,type);
            }

        };
        toolkit.adsManager.callbackRegister(tag, callback);
        toolkit.adsManager.request(tag, requestId, new AdsRequest.ReaderParameter(
                Const.Ads.Test.userId,
                new ArrayList<Zone>() {{
                    add(new Zone(zoneId, Zone.AdsType.popup));
                }},
                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
        ));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolkit.adsManager.callbackUnregister(tag);
    }


}