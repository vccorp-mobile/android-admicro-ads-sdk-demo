package vcc.viv.ads.demo.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsCatfishBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsCatFishFragment extends Fragment {
    private static final String tag = DetailAdsCatFishFragment.class.getSimpleName();

    private final String zoneId = "2019569";
    private final String requestId = "1";
    private Toolkit toolkit = Toolkit.newInstance();
    private int durationAnimation;
    private String data;

    private FragmentDetailAdsCatfishBinding binding;

    public static DetailAdsCatFishFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsCatFishFragment myFragment = new DetailAdsCatFishFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

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
        Context context = getContext();

        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                toolkit.adsManager.addAds(AdsForm.catFish, binding.content, tag, requestId, adsInfo.get(0).zoneId);
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
            }

            @Override
            public void closeWebViewAdsSuccess(String id, String requestId, String zoneId) {
                super.closeWebViewAdsSuccess(id, requestId, zoneId);
                if (!tag.equals(id)) return;
            }

            @Override
            public void showExpandAds(String id, String requestId, String zoneId) {
                super.showExpandAds(id, requestId, zoneId);
            }

            @Override
            public void showSmallAds(String id, String requestId, String zoneId) {
                super.showSmallAds(id, requestId, zoneId);
                Animation animation = new TranslateAnimation(0, 0, 600, 0);
                animation.setDuration(500);
                animation.setFillAfter(true);
                binding.content.getChildAt(0).startAnimation(animation);
            }

            @Override
            public void durationAnimationAds(String id, String requestId, String zoneId, int duration) {
                super.durationAnimationAds(id, requestId, zoneId, duration);
                durationAnimation = duration;
            }

            @Override
            public void resize(String id, String requestId, String zoneId, int width, int height) {
                super.resize(id, requestId, zoneId, width, height);
            }
        };

        toolkit.adsManager.callbackRegister(tag, callback);

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