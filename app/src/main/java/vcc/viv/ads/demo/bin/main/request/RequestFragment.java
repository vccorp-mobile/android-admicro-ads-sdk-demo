package vcc.viv.ads.demo.bin.main.request;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentRequestBinding;

public class RequestFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private FragmentRequestBinding binding;
    private RequestPagerAdapter adapter;

    private CallBack callBack;
    private ViewPager2.OnPageChangeCallback pageChangeCallback;
    private float initialXValue = 300;
    private int pagePosition;

    /*
     * Area : Override
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBack) {
            callBack = (CallBack) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
        binding.viewpager.unregisterOnPageChangeCallback(pageChangeCallback);
    }

    /*
     * Area : Function
     */
    private void initView() {
        toolkit.logger.info("init request views menu");
        adapter = new RequestPagerAdapter(getActivity());
        binding.viewpager.setAdapter(adapter);

        toolkit.logger.info("init title page");
        new TabLayoutMediator(binding.tab, binding.viewpager, (tab, position) -> {
            RequestPagerAdapter.Type[] values = RequestPagerAdapter.Type.values();
            RequestPagerAdapter.Type type = values[position % values.length];
            if (type == RequestPagerAdapter.Type.Request) {
                tab.setText("Request");
            } else {
                tab.setText("Log");
            }
        }).attach();

        toolkit.logger.info("init page callback");
        pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pagePosition = position;
            }
        };
        binding.viewpager.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    initialXValue = event.getX();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    try {
                        float diffX = event.getX() - initialXValue;
                        if (diffX < 0) {
                            if (pagePosition == 1) {
                                callBack.onSwipePage();
                            }
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback);
    }

    /*
     * Area : Inner Class
     */
    public interface CallBack {
        void onSwipePage();
    }

}
