package vcc.viv.ads.demo.bin.main.request.log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vcc.viv.ads.bin.AdsLogger;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.databinding.FragmentRequestLogBinding;
import vcc.viv.ads.demo.databinding.FragmentRequestLogBindingImpl;

public class RequestLogFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private final String tag = RequestLogFragment.class.getSimpleName();

    private FragmentRequestLogBinding binding;

    /*
     * Area : Override
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRequestLogBindingImpl.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolkit.logger.info("register app logger");
        toolkit.logger.callbackRegister(tag, (tag, msg) -> {
            log(msg);
        });

        toolkit.logger.info("register ads sdk logger");
        AdsLogger.getInstance().callbackRegister(tag, (tag, msg) -> {
            log(msg);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        toolkit.logger.info("unregister app logger");
        toolkit.logger.callbackUnregister(tag);

        toolkit.logger.info("unregister app logger");
        AdsLogger.getInstance().callbackUnregister(tag);
    }

    /*
     * Area : Function
     */
    private void log(String msg) {
        getActivity().runOnUiThread(() -> {
            StringBuilder build = new StringBuilder();
            build.append(binding.content.getText());
            build.append(msg);
            binding.content.setText(build.toString());
        });
    }

    /*
     * Area : Inner Class
     */
}
