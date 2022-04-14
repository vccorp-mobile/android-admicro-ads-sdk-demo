package vcc.viv.ads.demo.bin.demo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsItemBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsNativeFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsNativeFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private final String homeZoneId = "2016084";
    private final String detailZoneId = "2016084";
    private final String requestId = "1";

    private FragmentDetailAdsItemBinding binding;

    /*
     * Area : Instance
     */
    public static DetailAdsNativeFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(parameterData, type);

        DetailAdsNativeFragment myFragment = new DetailAdsNativeFragment();
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
        binding = FragmentDetailAdsItemBinding.inflate(inflater, container, false);
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
                AdsData info = toolkit.adsManager.addAds(binding.content, tag, requestId, adsInfo.get(0).zoneId);
                toolkit.logger.debug(toolkit.gson.toJson(info));
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
                toolkit.logger.warning(String.format(Const.Error.error, id + " - " + msg));
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

        toolkit.logger.info("verify argument");
        final int type;
        if (getArguments() != null) {
            type = getArguments().getInt(parameterData);
        } else {
            type = R.string.native_home;
        }
        AdsRequest.ReaderParameter parameter = new AdsRequest.ReaderParameter(
                Const.Ads.Test.userId,
                new ArrayList<String>() {{
                }},
                Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
        );
        switch (type) {
            case R.string.native_home:
                toolkit.logger.info("request native home ads");
                parameter.zones.add(homeZoneId);
                toolkit.adsManager.request(tag, requestId, parameter);
                parameter.isTestNative = AdsRequest.NativeTest.HOME;

                toolkit.logger.info("test true view");
                new Handler().postDelayed(() -> toolkit.adsManager.visibility(tag, requestId, homeZoneId, true, 1), 2000);
                break;
            case R.string.native_detail:
                toolkit.logger.info("request native detail ads");
                parameter.zones.add(detailZoneId);
                toolkit.adsManager.request(tag, requestId, parameter);
                parameter.isTestNative = AdsRequest.NativeTest.DETAIL;

                toolkit.logger.info("test true view");
                new Handler().postDelayed(() -> toolkit.adsManager.visibility(tag, requestId, homeZoneId, true, 1), 2000);
                break;
            default:
                String msg = String.format("data.tag id[%s] - string[%s]", type, resources.getString(type));
                toolkit.logger.warning(String.format(Const.Error.notSupport, msg));
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