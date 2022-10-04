package vcc.viv.ads.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.bin.BaseActivity;
import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.detail.DetailAdsCatFishFragment;
import vcc.viv.ads.demo.detail.DetailAdsInPageFragment;
import vcc.viv.ads.demo.detail.DetailAdsNativeFragment;
import vcc.viv.ads.demo.detail.DetailAdsNonInPage;
import vcc.viv.ads.demo.detail.DetailAdsPopupFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        binding = ActivityMainBinding.inflate(inflater);
        setContentView(binding.getRoot());

        List<String> item = new ArrayList<>();
        item.add("Popup");
        item.add("Popup-Form");
        item.add("Popup-TMDT");
        item.add("Popup-Game");
        item.add("Popup-LiveStream");
        item.add("Catfish");
        item.add("Inpage");
        item.add("NonInpage");
        item.add("Native-Home");
        item.add("Native-Detail");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);

        binding.list.setAdapter(adapter);
        binding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (item.get(i)) {
                case "Popup":
                    addFragment(DetailAdsPopupFragment.newInstance());
                    break;
                case "Popup-Form":
                    addFragment(DetailAdsPopupFragment.newInstance("form", "https://demo.admicro.vn/tuta/demo/ads-format-in-app-form/index.html"));
                    break;
                case "Popup-TMDT":
                    addFragment(DetailAdsPopupFragment.newInstance("ecommerce", "https://shopee.vn/unilever_vietnam"));
                    break;
                case "Popup-Game":
                    addFragment(DetailAdsPopupFragment.newInstance("game", "https://devminigame.ewings.vn/games/L002/?dev=1"));
                    break;
                case "Popup-LiveStream":
                    addFragment(DetailAdsPopupFragment.newInstance("livestream", "https://demo.admicro.vn/mobile/billboard/ban_270921.html"));
                    break;
                case "Catfish":
                    addFragment(DetailAdsCatFishFragment.newInstance());
                    break;
                case "Native-Home":
                    addFragment(DetailAdsNativeFragment.newInstance(1));
                    break;
                case "Native-Detail":
                    addFragment(DetailAdsNativeFragment.newInstance(0));
                    break;
                case "Inpage":
                    addFragment(DetailAdsInPageFragment.newInstance());
                    break;
                case "NonInpage":
                    addFragment(DetailAdsNonInPage.newInstance());
                    break;
                default:
            }
        });
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(binding.root.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
