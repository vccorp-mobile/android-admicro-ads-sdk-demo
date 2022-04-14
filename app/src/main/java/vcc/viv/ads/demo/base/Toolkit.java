package vcc.viv.ads.demo.base;

import android.app.Activity;

import com.google.gson.Gson;

import vcc.viv.ads.bin.AdsLogger;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.InitializeParameter;
import vcc.viv.ads.demo.BuildConfig;
import vcc.viv.ads.demo.util.Const;
import vcc.viv.ads.demo.util.Logger;

public class Toolkit {
    /*
     * Area : Variable
     */
    public Logger logger;
    public Gson gson;
    public AdsManager adsManager;

    /*
     * Area : Constructor
     */
    public Toolkit() {
        logger = Logger.getInstance();
        adsManager = AdsManager.getInstance();
        gson = new Gson();
    }

    /*
     * Area : Function
     */
    public void initAdsSdk(Activity activity) {
        logger.info("create parameter");
        InitializeParameter parameter = new InitializeParameter()
                .setCore(activity, Const.appId, BuildConfig.VERSION_CODE + "")                    // required
                .setLogger(true, AdsLogger.Level.info, "AdsSDK");                // optional
        adsManager = AdsManager.getInstance();
        adsManager.initialize(parameter);
    }

    public void settingLogger() {
        logger.setLog(true);
        logger.setLevel(Logger.Level.info);
        logger.setTag(AdsLogger.class.getSimpleName());
    }

    /*
     * Area : Inner Class
     */

}
