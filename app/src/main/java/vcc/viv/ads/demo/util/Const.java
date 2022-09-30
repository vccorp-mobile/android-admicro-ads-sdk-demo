package vcc.viv.ads.demo.util;

import java.util.ArrayList;
import java.util.List;

public interface Const {
    String appId = "vcc.mobilenewsreader.kenh14";

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
}
