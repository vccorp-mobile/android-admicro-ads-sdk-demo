package vcc.viv.ads.demo.bin.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import vcc.viv.ads.bin.WebViewType;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.bin.main.demo.DemoAdapter;
import vcc.viv.ads.demo.databinding.ActivityDetailBinding;
import vcc.viv.ads.demo.util.Const;

public class DetailActivity extends BaseActivity {
    /*
     * Area : Variable
     */
    private static final String tag = DetailActivity.class.getSimpleName();
    private static final String parameterData = tag + ":parameter:data";

    private ActivityDetailBinding binding;

    /*
     * Area : Starter
     */
    public static void starter(Context context, DemoAdapter.Data data) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(parameterData, new Gson().toJson(data));
        context.startActivity(intent);
    }

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolkit.logger.info("variable");
        Intent intent = getIntent();
        Resources resources = getResources();
        LayoutInflater inflater = LayoutInflater.from(this);

        toolkit.logger.info("content view");
        binding = ActivityDetailBinding.inflate(inflater);
        setContentView(binding.root);

        toolkit.logger.info("verify intent data");
        String dataString = intent.getStringExtra(parameterData);
        if (TextUtils.isEmpty(dataString)) {
            toolkit.logger.warning(String.format(Const.Error.empty, dataString));
            finish();
        } else {
            DemoAdapter.Data data = toolkit.gson.fromJson(dataString, DemoAdapter.Data.class);
            if (data == null) {
                toolkit.logger.warning(String.format(Const.Error.nullPoint, "DemoAdapter.Data"));
                finish();
            } else if (TextUtils.isEmpty(resources.getString(data.tag))) {
                toolkit.logger.warning(String.format(Const.Error.empty, "DemoAdapter.Data.tag"));
                finish();
            } else if (data.type == DemoAdapter.Type.synthetic) {

            } else if (data.type == DemoAdapter.Type.form) {
                addFragment(getFragmentForm(data));
            } else if (data.type == DemoAdapter.Type.android) {
                addFragment(getFragmentNative(data));
            } else if (data.type == DemoAdapter.Type.web) {
                addFragment(getFragmentWeb(data));
            } else {
                toolkit.logger.warning(String.format(Const.Error.notSupport, data.tag));
                finish();
            }
        }
    }

    /*
     * Area : Function
     */
    private void addFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(binding.root.getId(), fragment);
        transaction.commit();
    }

    private BaseFragment getFragmentForm(DemoAdapter.Data data) {
        Resources resources = getResources();
        switch (data.tag) {
            case R.string.form_popup:
                return DetailAdsPopupFragment.newInstance();
            case R.string.form_in_page:
                return DetailAdsInPageFragment.newInstance();
            case R.string.form_cat_fish:
                return new BaseFragment();
            default:
                String msg = String.format("data.tag id[%s] - string[%s]", data.tag, resources.getString(data.tag));
                toolkit.logger.warning(String.format(Const.Error.notSupport, msg));
                return new BaseFragment();
        }
    }

    private BaseFragment getFragmentNative(DemoAdapter.Data data) {
        Resources resources = getResources();
        switch (data.tag) {
            case R.string.native_home:
            case R.string.native_detail:
                return DetailAdsNativeFragment.newInstance(data.tag);
            default:
                String msg = String.format("data.tag id[%s] - string[%s]", data.tag, resources.getString(data.tag));
                toolkit.logger.warning(String.format(Const.Error.notSupport, msg));
                return new BaseFragment();
        }
    }

    private BaseFragment getFragmentWeb(DemoAdapter.Data data) {
        Resources resources = getResources();
        final String format;
        switch (data.tag) {
            case R.string.web_top:
                format = WebViewType.top.tag;
                break;
            case R.string.web_billboard:
                format = WebViewType.top.billboard.tag;
                break;
            case R.string.web_medium:
                format = WebViewType.medium.tag;
                break;
            case R.string.web_adx_native:
                format = WebViewType.adxNative.tag;
                break;
            case R.string.web_adx_sponsor_180_200:
                format = WebViewType.adxSponsor180200.tag;
                break;
            case R.string.web_adx_sponsor_300_250:
                format = WebViewType.adxSponsor300250.tag;
                break;
            case R.string.web_lotus_shop_chat:
                format = WebViewType.lotusShopChat.tag;
                break;
            case R.string.web_poster:
                format = WebViewType.iPoster.tag;
                break;
            case R.string.web_in_page:
                format = WebViewType.inPage.tag;
                break;
            case R.string.web_native:
                format = WebViewType.nativeView.tag;
                break;
            case R.string.web_native_video:
                format = WebViewType.nativeVideo.tag;
                break;
            case R.string.web_native_live:
                format = WebViewType.playerLive.tag;
                break;
            case R.string.web_deeplink:
                format = WebViewType.deeplink.tag;
                break;
            case R.string.web_no_deeplink:
                format = WebViewType.noDeeplink.tag;
                break;
            case R.string.true_view:
                format = WebViewType.playerLive.tag;
                return DetailAdsTrueViewFragment.newInstance(format);
            default:
                String msg = String.format("data.tag id[%s] - string[%s]", data.tag, resources.getString(data.tag));
                toolkit.logger.warning(String.format(Const.Error.notSupport, msg));
                return new BaseFragment();
        }
        return DetailAdsWebFragment.newInstance(format);
    }

    /*
     * Area : Inner Class
     */
}