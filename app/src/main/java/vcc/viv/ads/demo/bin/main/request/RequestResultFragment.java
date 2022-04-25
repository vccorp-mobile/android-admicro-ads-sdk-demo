package vcc.viv.ads.demo.bin.main.request;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.bin.AdsData;
import vcc.viv.ads.bin.AdsManager;
import vcc.viv.ads.bin.AdsManagerCallback;
import vcc.viv.ads.bin.AdsRequest;
import vcc.viv.ads.bin.Zone;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.base.BaseFragment;
import vcc.viv.ads.demo.bin.common.TagAdapter;
import vcc.viv.ads.demo.databinding.FragmentRequestResultBinding;
import vcc.viv.ads.demo.util.Const;
import vcc.viv.ads.demo.util.Event;

public class RequestResultFragment extends BaseFragment implements Event {
    /*
     * Area : Variable
     */
    private final String tag = RequestResultFragment.class.getSimpleName();

    private FragmentRequestResultBinding binding;
    private RequestResultAdapter adapter;
    private RequestResultViewModel viewModel;

    private BottomSheetBehavior bottomSheetBehavior;
    private TagAdapter zonesAdapter;

    private String requestId = "1";

    /*
     * Area : Override
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentRequestResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initSetting();
        initRequestConfigure();
    }

    @Override
    public boolean onFragmentBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return true;
        } else {
            return false;
        }
    }

    /*
     * Area : Function
     */
    private void initView() {
        toolkit.logger.info("init ads results");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        binding.adsList.setLayoutManager(manager);

        adapter = new RequestResultAdapter();
        binding.adsList.setAdapter(adapter);

        toolkit.logger.info("init refresh");
        binding.refresh.setOnRefreshListener(() -> {
            toolkit.logger.info("refresh ads");
            if (zonesAdapter.getItemCount() == 0) {
                binding.refresh.setRefreshing(false);
                Snackbar.make(binding.mainContent, getResources().getString(R.string.notice_zone_empty), BaseTransientBottomBar.LENGTH_SHORT).show();
            } else {
                requestAds();
            }
        });

        toolkit.logger.info("init more button");
        binding.moreButton.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.middleLayout.setVisibility(View.VISIBLE);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        toolkit.logger.info("init middle layout");
        binding.middleLayout.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.middleLayout.setVisibility(View.GONE);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void initSetting() {
        toolkit.logger.info("init view model");
        viewModel = ViewModelProviders.of(getActivity()).get(RequestResultViewModel.class);
        viewModel.getViewList().observe(getViewLifecycleOwner(), data -> {
            if (data == null) {
                toolkit.logger.warning(String.format(Const.Error.nullPoint, "data in adapter"));
            } else if (data.size() <= 0) {
                toolkit.logger.warning(String.format(Const.Error.empty, "data in adapter"));
            } else {
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                binding.refresh.setRefreshing(false);
            }
        });

        toolkit.logger.info("register callback with ads manage");
        AdsManagerCallback callback = new AdsManagerCallback() {
            @Override
            public void requestAdsSuccess(String id, String requestId, List<AdsManager.AdsInfo> data) {
                super.requestAdsSuccess(id, requestId, data);
                if (!tag.equals(id)) return;
                List<RequestResultAdapter.AdsInfo> adsInfo = new ArrayList<>();
                for (AdsManager.AdsInfo dataItem : data) {
                    RequestResultAdapter.AdsInfo item = new RequestResultAdapter.AdsInfo(new ConstraintLayout(getContext()), dataItem.zoneId);
                    AdsData info = toolkit.adsManager.addAds(item.view, tag, requestId, dataItem.zoneId);
                    adsInfo.add(item);
                    toolkit.logger.debug(toolkit.gson.toJson(info));
                }
                getActivity().runOnUiThread(() -> viewModel.setViewList(adsInfo));
            }

            @Override
            public void requestAdsFail(String id, String requestId, String msg) {
                super.requestAdsFail(id, requestId, msg);
                if (!tag.equals(id)) return;
                toolkit.logger.warning(String.format(Const.Error.error, id + " - " + msg));
                binding.refresh.setRefreshing(false);
                Snackbar.make(binding.mainContent, getResources().getString(R.string.notice_zone_not_exist), BaseTransientBottomBar.LENGTH_SHORT).show();

            }

            @Override
            public void loadAdsStart(String id, String requestId, String zoneId) {
                super.loadAdsStart(id, requestId, zoneId);
            }

            @Override
            public void loadAdsFinish(String id, String requestId, String zoneId) {
                super.loadAdsFinish(id, requestId, zoneId);
            }

            @Override
            public void closeWebViewAds(String id, String requestId, String zoneId) {
                super.closeWebViewAds(id, requestId, zoneId);
                if (!tag.equals(id)) return;
                Snackbar.make(binding.mainContent, "closeWebViewAds", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        };
        toolkit.adsManager.callbackRegister(tag, callback);
    }

    private void initRequestConfigure() {
        toolkit.logger.info("init bottom sheet behavior");
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.middleLayout.setVisibility(View.GONE);
                    closeKeyboard();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        toolkit.logger.info("init zone id tag");
        zonesAdapter = new TagAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.bottomSheet.zones.setLayoutManager(layoutManager);
        binding.bottomSheet.zones.setAdapter(zonesAdapter);

        toolkit.logger.info("init zone input");
        binding.bottomSheet.zoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String zoneId = s.toString().trim();
                    if (!zoneId.isEmpty()) {
                        toolkit.logger.info("add zone id");
                        zonesAdapter.addZone(zoneId);
                        zonesAdapter.notifyDataSetChanged();
                        binding.bottomSheet.zones.scrollToPosition(zonesAdapter.getItemCount() - 1);
                    }
                    binding.bottomSheet.zoneInput.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        toolkit.logger.info("init zone input key");
        binding.bottomSheet.zoneInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                String zoneId = binding.bottomSheet.zoneInput.getText().toString().trim();
                if (!zoneId.isEmpty()) {
                    zonesAdapter.addZone(zoneId);
                    zonesAdapter.notifyDataSetChanged();
                    binding.bottomSheet.zoneInput.setText("");
                } else {
                    if (zonesAdapter.getItemCount() == 0) {
                        Snackbar.make(binding.mainContent, getResources().getString(R.string.notice_zone_empty), BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else {
                        return true;
                    }
                }
                return true;
            }
            return false;
        });

        toolkit.logger.info("init bottom request");
        binding.bottomSheet.requestButton.setOnClickListener(v -> {
            toolkit.logger.info("request ads");
            if (!binding.bottomSheet.zoneInput.getText().toString().isEmpty()) {
                zonesAdapter.addZone(binding.bottomSheet.zoneInput.getText().toString().trim());
                zonesAdapter.notifyDataSetChanged();
                binding.bottomSheet.zoneInput.setText("");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            requestAds();
        });
    }

    private void requestAds() {
        toolkit.logger.info("request ads");
        if (zonesAdapter == null) {
            toolkit.logger.warning(String.format(Const.Error.nullException, "adapter"));
        } else if (zonesAdapter.getData().size() <= 0) {
            toolkit.logger.info("list empty");
            binding.refresh.setRefreshing(false);
            Snackbar.make(binding.mainContent, getResources().getString(R.string.notice_zone_empty), BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            List<Zone> zones = new ArrayList<>();
            for (int i = 0; i < zonesAdapter.getData().size(); i++) {
                zones.add(new Zone(zonesAdapter.getData().get(i), null));
            }

            toolkit.adsManager.request(tag, requestId, new AdsRequest.ReaderParameter(
                    Const.Ads.Test.userId,
                    zones,
                    Const.Ads.Test.positions, Const.Ads.Test.url, Const.Ads.Test.channel
            ));
        }
    }

    private void closeKeyboard() {
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
        } else {
            toolkit.logger.error(Const.Error.nullPoint, "current focus");
        }
    }

    /*
     * Area : Inner Class
     */
}
