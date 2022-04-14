package vcc.viv.ads.demo.bin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.databinding.ItemAdsBinding;

public class RequestResultAdapter extends RecyclerView.Adapter<RequestResultAdapter.ViewHolder> {
    /*
     * Area : Variable
     */
    private List<AdsInfo> adsInfo;

    /*
     * Area : Constructor
     */
    public RequestResultAdapter() {
        this.adsInfo = new ArrayList<>();
    }

    /*
     * Area : Override
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(ItemAdsBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.zoneIdText.setText(adsInfo.get(position).zoneId);
        if (adsInfo.get(position).view.getParent() == null) {
            holder.binding.root.removeAllViews();
            holder.binding.root.addView(adsInfo.get(position).view);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return adsInfo.size();
    }

    /*
     * Area : Function
     */
    public void setData(List<AdsInfo> adsInfoList) {
        if (adsInfoList != null) {
            this.adsInfo.clear();
            this.adsInfo.addAll(adsInfoList);
            notifyDataSetChanged();
        }
    }

    /*
     * Area : Inner class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAdsBinding binding;

        public ViewHolder(ItemAdsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public static class AdsInfo {
        public ConstraintLayout view;
        public String zoneId;

        public AdsInfo(ConstraintLayout view, String zoneID) {
            this.view = view;
            this.zoneId = zoneID;
        }
    }
}