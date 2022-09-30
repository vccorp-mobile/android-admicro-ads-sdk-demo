package vcc.viv.ads.demo.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsItemBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsNativeFragment extends Fragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsNativeFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private final String homeZoneId = "2016084";
    private final String detailZoneId = "2016084";
    private final String requestId = "1";
    private Toolkit toolkit = new Toolkit();

    private FragmentDetailAdsItemBinding binding;

    public static DetailAdsNativeFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(parameterData, type);
        DetailAdsNativeFragment myFragment = new DetailAdsNativeFragment();
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
        binding = FragmentDetailAdsItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                toolkit.adsManager.addAds(binding.content, tag, requestId, adsInfo.get(0).zoneId);
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

        final int type;
        if (getArguments() != null) {
            type = getArguments().getInt(parameterData);
        } else {
            type = 1;
        }
        AdsRequest.ReaderParameter parameter = new AdsRequest.ReaderParameter(
                Const.Ads.Test.userId,
                new ArrayList<Zone>() {{
                }},
                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
        );
        switch (type) {
            case 1:
                parameter.zones.add(new Zone(homeZoneId, Zone.AdsType.noninpage));
                toolkit.adsManager.request(tag, requestId, parameter);
                parameter.isTestNative = AdsRequest.NativeTest.home;
                break;
            case 0:
                parameter.zones.add(new Zone(detailZoneId, Zone.AdsType.noninpage));
                toolkit.adsManager.request(tag, requestId, parameter);
                parameter.isTestNative = AdsRequest.NativeTest.detail;
                break;
            default:
                break;
        }
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