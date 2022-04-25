package vcc.viv.ads.demo.bin.demo.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.databinding.FragmentDemoHeaderBinding;
import vcc.viv.ads.demo.databinding.FragmentDemoItemBinding;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {
    /*
     * Area : Variable
     */
    private List<Data> data;
    private Callback callback;

    /*
     * Area : Constructor
     */
    public DemoAdapter(Callback callback) {
        this.data = new ArrayList<>();
        this.callback = callback;
    }

    /*
     * Area : Override
     */
    @Override
    public int getItemViewType(int position) {
        int type = 0;
        try {
            type = data.get(position).viewType.ordinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewType[] types = ViewType.values();
        ViewType type = types[viewType % types.length];
        switch (type) {
            case header:
                return new HeaderViewHolder(FragmentDemoHeaderBinding.inflate(inflater, viewGroup, false));
            case item:
                return new ItemViewHolder(FragmentDemoItemBinding.inflate(inflater, viewGroup, false));
        }
        return new ViewHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Data item = data.get(position);
        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).bindView(item, position);
        } else if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).bindView(item, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = data != null ? data.size() : 0;
        return count;
    }

    /*
     * Area : Function
     */
    public void setData(List<Data> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
        }
    }

    /*
     * Area : Inner Class
     */
    public enum Type {
        synthetic, form, android, web
    }

    public enum ViewType {
        header, item
    }

    public interface Callback {
        void itemClick(Data data, int position);
    }

    public static class Data {
        /*
         * Area : Variable
         */
        public int tag;
        public ViewType viewType;
        public Type type;

        /*
         * Area : Constructor
         */
        public Data(int tag, Type type, ViewType viewType) {
            this.tag = tag;
            this.type = type;
            this.viewType = viewType;
        }

        /*
         * Area : Function
         */
    }

    public class HeaderViewHolder extends ViewHolder {
        /*
         * Area : Variable
         */
        private final FragmentDemoHeaderBinding binding;

        /*
         * Area : Constructor
         */
        public HeaderViewHolder(FragmentDemoHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /*
         * Area : Function
         */
        public void bindView(Data data, int position) {
            Resources resources = binding.getRoot().getContext().getResources();
            binding.content.setText(resources.getString(data.tag));
            binding.getRoot().setOnClickListener(v -> {
            });
        }
    }

    public class ItemViewHolder extends ViewHolder {
        /*
         * Area : Variable
         */
        private final FragmentDemoItemBinding binding;

        /*
         * Area : Constructor
         */
        public ItemViewHolder(FragmentDemoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /*
         * Area : Function
         */
        public void bindView(Data data, int position) {
            Resources resources = binding.getRoot().getContext().getResources();
            binding.content.setText(resources.getString(data.tag));
            binding.getRoot().setOnClickListener(v -> {
                if (callback != null) {
                    callback.itemClick(data, position);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
         * Area : Variable
         */

        /*
         * Area : Constructor
         */
        public ViewHolder(View view) {
            super(view);
        }

        /*
         * Area : Function
         */
    }
}