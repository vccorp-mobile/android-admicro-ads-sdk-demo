package vcc.viv.ads.demo.bin.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.WebViewType;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsItemBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsWebFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private static final String tag = DetailAdsWebFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private FragmentDetailAdsItemBinding binding;

    private final String requestId = "1";

    /*
     * Area : Instance
     */
    public static DetailAdsWebFragment newInstance(String format) {
        Bundle args = new Bundle();
        args.putString(parameterData, format);

        DetailAdsWebFragment myFragment = new DetailAdsWebFragment();
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
            @Override
            public void loadAdsStart(String id, String requestId, String zoneId) {
                super.loadAdsStart(id, requestId, zoneId);
            }

            @Override
            public void loadAdsFinish(String id, String requestId, String zoneId) {
                super.loadAdsFinish(id, requestId, zoneId);
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);

        toolkit.logger.info("verify argument");
        final String format;
        if (getArguments() != null) {
            format = getArguments().getString(parameterData);
        } else {
            format = "";
        }
        if (TextUtils.isEmpty(format)) {
            toolkit.logger.warning(String.format(Const.Error.empty, "format"));
        } else {
            WebViewType type = WebViewType.getType(format);
            if (type == null) {
                toolkit.logger.warning(String.format(Const.Error.notSupport, format));
            } else {
                toolkit.logger.info("request ads");
                List<WebViewType> formats = new ArrayList();
                formats.add(type);
                toolkit.adsManager.request(tag, requestId, formats);
            }
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