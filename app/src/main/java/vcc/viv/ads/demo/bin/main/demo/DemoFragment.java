package vcc.viv.ads.demo.bin.main.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.bin.demo.DetailActivity;
import vcc.viv.ads.demo.databinding.FragmentDemoBinding;
import vcc.viv.ads.demo.util.Const;

public class DemoFragment extends BaseFragment {
    /*
     * Area : Variable
     */
    private final String tag = DemoFragment.class.getSimpleName();

    private FragmentDemoBinding binding;
    private DemoViewModel viewModel;
    private DemoAdapter adapter;

    /*
     * Area : Override
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDemoBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolkit.logger.info("create variable");
        Context context = getContext();

        toolkit.logger.info("init demos menu");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setLayoutManager(layoutManager);

        adapter = new DemoAdapter((data, position) -> {
            toolkit.logger.debug(toolkit.gson.toJson(data));
            DetailActivity.starter(getContext(), data);
        });
        binding.recycler.setAdapter(adapter);

        toolkit.logger.info("init view model");
        viewModel = new DemoViewModel(context);
        viewModel.getDemos().observe(getViewLifecycleOwner(), data -> {
            if (data == null) {
                toolkit.logger.warning(String.format(Const.Error.nullPoint, "data in adapter"));
            } else if (data.size() <= 0) {
                toolkit.logger.warning(String.format(Const.Error.empty, "data in adapter"));
            } else {
                toolkit.logger.info("adapter data");
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
}
