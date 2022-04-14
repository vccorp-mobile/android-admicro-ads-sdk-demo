package vcc.viv.ads.demo.bin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.util.Event;

public class MainActivity extends BaseActivity {
    /*
     * Area : Variable
     */
    private final String tag = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private boolean doubleBack = false;

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolkit.logger.info("logger");
        toolkit.settingLogger();

        toolkit.logger.info("ads manager");
        toolkit.initAdsSdk(this);

        toolkit.logger.info("variable");
        LayoutInflater inflater = LayoutInflater.from(this);

        toolkit.logger.info("content view");
        binding = ActivityMainBinding.inflate(inflater);
        setContentView(binding.getRoot());

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        toolkit.logger.info("call back");
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof Event) {
                boolean isBottomSheetHide = ((Event) fragment).onFragmentBackPressed();
                if (isBottomSheetHide) return;
            }
        }
        if (doubleBack) {
            super.onBackPressed();
            finish();
        }
        this.doubleBack = true;
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.notice_twice_back), Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBack = false, 2000);
    }

    /*
     * Area : Function
     */
    private void initView() {
        getSupportFragmentManager().beginTransaction().add(binding.container.getId(),new RequestResultFragment()).commit();
    }

    /*
     * Area : Inner Class
     */

}