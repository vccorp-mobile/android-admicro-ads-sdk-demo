package vcc.viv.ads.demo.bin.main.demo;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseViewModel;

public class DemoViewModel extends BaseViewModel {
    /*
     * Area : Variable
     */
    private MutableLiveData<List<DemoAdapter.Data>> demos;


    /*
     * Area : Constructor
     */
    public DemoViewModel(Context context) {
        super(context);
        this.demos = new MutableLiveData();
    }

    /*
     * Area : Public Function
     */
    public MutableLiveData<List<DemoAdapter.Data>> getDemos() {
        toolkit.logger.info("start");
        List<DemoAdapter.Data> data = demos.getValue();
        if (data == null) {
            data = loadData();
        }
        demos.setValue(data);
        return demos;
    }

    /*
     * Area : Private Function
     */
    private List<DemoAdapter.Data> loadData() {
        toolkit.logger.info("start");
        List<DemoAdapter.Data> data = new ArrayList();

//        data.add(new DemoAdapter.Data(R.string.demo_header_synthetic, DemoAdapter.Type.synthetic, DemoAdapter.ViewType.header));
//        data.add(new DemoAdapter.Data(R.string.synthetic_all_in_one, DemoAdapter.Type.synthetic, DemoAdapter.ViewType.item));
//        data.add(new DemoAdapter.Data(R.string.synthetic_true_view, DemoAdapter.Type.synthetic, DemoAdapter.ViewType.item));

        data.add(new DemoAdapter.Data(R.string.demo_header_form, DemoAdapter.Type.form, DemoAdapter.ViewType.header));
        data.add(new DemoAdapter.Data(R.string.form_popup, DemoAdapter.Type.form, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.form_in_page, DemoAdapter.Type.form, DemoAdapter.ViewType.item));
//        data.add(new DemoAdapter.Data(R.string.form_cat_fish, DemoAdapter.Type.form, DemoAdapter.ViewType.item));

        data.add(new DemoAdapter.Data(R.string.demo_header_native, DemoAdapter.Type.android, DemoAdapter.ViewType.header));
        data.add(new DemoAdapter.Data(R.string.native_home, DemoAdapter.Type.android, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.native_detail, DemoAdapter.Type.android, DemoAdapter.ViewType.item));

        data.add(new DemoAdapter.Data(R.string.demo_header_web, DemoAdapter.Type.web, DemoAdapter.ViewType.header));
        data.add(new DemoAdapter.Data(R.string.web_top, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_billboard, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_medium, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_adx_native, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_adx_sponsor_180_200, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_adx_sponsor_300_250, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_lotus_shop_chat, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_poster, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_in_page, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_native, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_native_video, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_native_live, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_deeplink, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.web_no_deeplink, DemoAdapter.Type.web, DemoAdapter.ViewType.item));
        data.add(new DemoAdapter.Data(R.string.true_view, DemoAdapter.Type.web, DemoAdapter.ViewType.item));

        return data;
    }
}
