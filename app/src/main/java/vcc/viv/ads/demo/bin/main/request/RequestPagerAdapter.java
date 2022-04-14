package vcc.viv.ads.demo.bin.main.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vcc.viv.ads.demo.bin.main.request.detail.RequestResultFragment;
import vcc.viv.ads.demo.bin.main.request.log.RequestLogFragment;

public class RequestPagerAdapter extends FragmentStateAdapter {
    /*
     * Area : Variable
     */

    /*
     * Area : Constructor
     */
    public RequestPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        if (type == Type.Log) {
            return new RequestLogFragment();
        } else {
            return new RequestResultFragment();
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
        Request, Log
    }
}