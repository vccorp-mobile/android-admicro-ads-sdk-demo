package vcc.viv.ads.demo.bin.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseActivity;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.util.Const;
import vcc.viv.ads.demo.util.Event;

public class MainActivity extends BaseActivity{
    /*
     * Area : Variable
     */
    private final String tag = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private MainAdapter pagerAdapter;

    private boolean doubleBack = false;
    private ViewPager2.OnPageChangeCallback pageChangeCallback;
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
        binding.viewpager.unregisterOnPageChangeCallback(pageChangeCallback);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.viewpager.getCurrentItem() != 0) {
            binding.viewpager.setCurrentItem(0, true);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /*
     * Area : Function
     */
    private void initView() {
        toolkit.logger.info("adapter");
        pagerAdapter = new MainAdapter(this);

        toolkit.logger.info("init viewpager");
        if (binding.navigation.getMenu().getItem(0).getItemId() == R.id.request) {
            binding.viewpager.setCurrentItem(MainAdapter.Type.Request.ordinal());
        } else {
            binding.viewpager.setCurrentItem(MainAdapter.Type.Demo.ordinal());
        }

        toolkit.logger.info("bottom navigation");
        binding.navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.request) {
                toolkit.logger.info("show request fragment");
                binding.viewpager.setCurrentItem(MainAdapter.Type.Request.ordinal(), true);
                return true;
            } else if (item.getItemId() == R.id.demo) {
                toolkit.logger.info("show demo fragment");
                binding.viewpager.setCurrentItem(MainAdapter.Type.Demo.ordinal(), true);
                return true;
            } else {
                toolkit.logger.info(String.format(Const.Error.notSupport, item.getItemId()));
                return false;
            }
        });

        toolkit.logger.info("init page call back");
        pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                toolkit.logger.debug(String.format("position[%s]", position));
                binding.navigation.getMenu().getItem(position).setChecked(true);
            }
        };

        toolkit.logger.info("viewpager");
        binding.viewpager.setAdapter(pagerAdapter);
        binding.viewpager.setUserInputEnabled(true);
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback);
    }

    /*
     * Area : Inner Class
     */

}