package vcc.viv.ads.demo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

import vcc.viv.ads.demo.bin.Toolkit;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.detail.DetailAdsCatFishFragment;
import vcc.viv.ads.demo.detail.DetailAdsInPageFragment;
import vcc.viv.ads.demo.detail.DetailAdsNativeFragment;
import vcc.viv.ads.demo.detail.DetailAdsNonInPage;
import vcc.viv.ads.demo.detail.DetailAdsPopupFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolkit toolkit = Toolkit.newInstance();
        toolkit.initAdsSdk(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        binding = ActivityMainBinding.inflate(inflater);
        setContentView(binding.getRoot());

        List<String> item = new ArrayList<>();
        item.add("Popup");
        item.add("Catfish");
        item.add("Inpage");
        item.add("NonInpage");
        item.add("Home");
        item.add("Detail");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);

        binding.list.setAdapter(adapter);
        binding.list.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (item.get(i)) {
                case "Popup":
                    addFragment(DetailAdsPopupFragment.newInstance());
                    break;
                case "Catfish":
                    addFragment(DetailAdsCatFishFragment.newInstance());
                    break;
                case "Inpage":
                    addFragment(DetailAdsInPageFragment.newInstance());
                    break;
                case "NonInpage":
                    addFragment(DetailAdsNonInPage.newInstance());
                    break;
                case "Home":
                    addFragment(DetailAdsNativeFragment.newInstance(R.string.native_home));
                    break;
                case "Detail":
                    addFragment(DetailAdsNativeFragment.newInstance(R.string.native_detail));
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
