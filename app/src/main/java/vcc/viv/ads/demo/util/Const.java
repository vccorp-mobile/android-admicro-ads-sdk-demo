package vcc.viv.ads.demo.util;

import java.util.ArrayList;
import java.util.List;

public interface Const {
    /*
     * Area : Common
     */
    String appId = "vcc.mobilenewsreader.kenh14";

    /*
     * Area : Ads
     */
    interface Ads {
        interface Test {
            String userId = "";
            String url = "https://app.kenh14.vn/home&v=5.1.51";
            String channel = "https://app.kenh14.vn/home";
            List<String> positions = new ArrayList<String>() {{
                add("1");
            }};
        }
    }

    /*
     * Area : Error
     */
    interface Error {
        String nullPoint = "Data[%s]'s null";
        String empty = "Data[%s]'s empty";
        String notSupport = "Data[%s]'s not support";
        String error = "Error[%s]";
        String nullException = "Exception[%s] null";
        String textEmpty = "String[%s] invalid. Null or empty";
        String dataError = "Data[%s] error";
        String keyNotFound = "Key[%s] not found";
    }

    /*
     * Area : ads
     */
    enum AdsForm {
        normal, catFish, inPage, popup
    }
}
