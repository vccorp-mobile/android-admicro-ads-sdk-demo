package vcc.viv.ads.demo.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.bin.adsenum.AdsForm;
import vcc.viv.ads.bin.adsenum.WebViewType;
import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsInpageBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsInPageFragment extends Fragment {
    private static final String tag = DetailAdsInPageFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private Toolkit toolkit = Toolkit.newInstance();
    private final String zoneId = "2013893";
    private final String requestId = "1";

    private FragmentDetailAdsInpageBinding binding;

    public static DetailAdsInPageFragment newInstance() {
        Bundle args = new Bundle();
        DetailAdsInPageFragment myFragment = new DetailAdsInPageFragment();
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
        binding = FragmentDetailAdsInpageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        binding.fakeView1.setOnClickListener(v -> Snackbar.make(view, "click fake view 1", Snackbar.LENGTH_SHORT).show());
        binding.fakeView2.setOnClickListener(v -> Snackbar.make(view, "click fake view 2", Snackbar.LENGTH_SHORT).show());
        binding.fakeBlank.setOnTouchListener((v, event) -> {
            toolkit.adsManager.click(event, tag, requestId, WebViewType.inPage.toString());
            return true;
        });

        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                toolkit.adsManager.addAds(AdsForm.inPage, binding.adsLayout, tag, requestId, adsInfo.get(0).zoneId);
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
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

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
}