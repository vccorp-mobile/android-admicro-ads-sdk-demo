package vcc.viv.ads.demo.bin.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.bin.main.demo.DemoFragment;
import vcc.viv.ads.demo.bin.main.request.RequestResultFragment;

public class MainAdapter extends FragmentStateAdapter {
    /*
     * Area : Variable
     */

    /*
     * Area : Constructor
     */
    public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /*
     * Area : Override
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Type[] values = Type.values();
        Type type = values[position % values.length];
        switch (type) {
            case Request:
                return new RequestResultFragment();
            case Demo:
                return new DemoFragment();
            default:
                return new BaseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return Type.values().length;
    }

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
    public enum Type {
        Request, Demo
    }
}