package vcc.viv.ads.demo.bin;

import android.app.Activity;

import androidx.databinding.library.baseAdapters.BuildConfig;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.InitializeParameter;
import vcc.viv.ads.demo.util.Const;

public class Toolkit {
    public AdsManager adsManager;

    public Toolkit() {
        adsManager = AdsManager.getInstance();
    }

    public void initAdsSdk(Activity activity) {
        InitializeParameter parameter = new InitializeParameter()
                .setCore(activity, Const.appId, BuildConfig.LIBRARY_PACKAGE_NAME + "");
        adsManager = AdsManager.getInstance();
        adsManager.initialize(parameter);
    }
}
