package vcc.viv.ads.demo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    /*
     * Area : Variable
     */
    private final String tag = BaseFragment.class.getSimpleName();
    protected Toolkit toolkit;

    /*
     * Area : Override
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolkit = new Toolkit();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
}