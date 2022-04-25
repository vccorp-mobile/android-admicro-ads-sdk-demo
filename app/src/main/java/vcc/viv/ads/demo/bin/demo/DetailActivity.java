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
                return DetailAdsCatFishFragment.newInstance();
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

    /*
     * Area : Inner Class
     */
}