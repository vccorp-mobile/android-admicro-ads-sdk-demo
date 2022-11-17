package vcc.viv.ads.demo.bin;

import android.app.Activity;

import androidx.databinding.library.baseAdapters.BuildConfig;

import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.InitializeParameter;
import vcc.viv.ads.bin.adsenum.AdsBrowser;
import vcc.viv.ads.demo.util.Const;

public class Toolkit {
    public AdsManager adsManager;
    private static Toolkit instance = null;
    public static Toolkit newInstance(){
        if(instance == null){
            instance = new Toolkit();
        }
        return instance;
    }

    public Toolkit() {
        adsManager = AdsManager.getInstance();
    }

    public void initAdsSdk(Activity activity) {
        InitializeParameter parameter = new InitializeParameter()
                .setCore(activity, Const.appId, BuildConfig.LIBRARY_PACKAGE_NAME + "")
                .setLimit(true)
                .setWebBrowser(AdsBrowser.inapp, null);
        adsManager = AdsManager.getInstance();
        adsManager.initialize(parameter);
    }
}
