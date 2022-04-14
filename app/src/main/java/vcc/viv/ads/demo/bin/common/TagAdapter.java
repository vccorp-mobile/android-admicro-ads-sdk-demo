package vcc.viv.ads.demo.bin.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    /*
     * Area : Variable
     */
    private List<String> data;

    /*
     * Area : Constructor
     */
    public TagAdapter() {
        this.data = new ArrayList<>();
    }

    /*
     * Area : Override
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.zoneIdText.setText(data.get(position));
        holder.cancelButton.setOnClickListener(v -> {
            data.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /*
     * Area : Function
     */
    public void addZone(String zoneIds) {
        if (zoneIds != null) {
            this.data.add(zoneIds);
        }
    }

    public List<String> getData() {
        return data;
    }

    /*
     * Area : Inner class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
         * Area : Variable
         */
        private TextView zoneIdText;
        private ImageView cancelButton;

        /*
         * Area : Constructor
         */
        public ViewHolder(View itemView) {
            super(itemView);
            zoneIdText = itemView.findViewById(R.id.content);
            cancelButton = itemView.findViewById(R.id.remove);
        }

        /*
         * Area : Function
         */
    }
}