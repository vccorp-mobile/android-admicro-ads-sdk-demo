package vcc.viv.ads.demo.bin.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsForm;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.WebViewType;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentDetailAdsTrueViewBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailAdsTrueViewFragment extends BaseFragment {
    private static final String tag = DetailAdsWebFragment.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";
    private FragmentDetailAdsTrueViewBinding binding;
    private final String requestId = "1";
    private String zoneId = "";

    public static DetailAdsTrueViewFragment newInstance(String format) {

        Bundle args = new Bundle();
        args.putString(parameterData, format);
        DetailAdsTrueViewFragment fragment = new DetailAdsTrueViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDetailAdsTrueViewBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolkit.logger.info("register callback with ads manager");
        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> adsInfo) {
                super.requestAdsSuccess(id, requestId, adsInfo);
                if (!tag.equals(id)) return;
                zoneId = adsInfo.get(0).zoneId;
                AdsData info = toolkit.adsManager.addAds(AdsForm.inPage, binding.adsLayout, tag, requestId, adsInfo.get(0).zoneId);
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

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolkit.adsManager.visibility(tag,requestId,zoneId,true,1);
            }
        });

        binding.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolkit.adsManager.visibility(tag,requestId,zoneId,false,0);
            }
        });

    }
}
