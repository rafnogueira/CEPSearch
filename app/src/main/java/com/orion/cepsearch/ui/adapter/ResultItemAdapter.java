package com.orion.cepsearch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.databinding.CepResultItemBinding;
import com.orion.cepsearch.ui.settings.SettingsFragment;

import java.util.List;

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.ItemViewHolder> {

    private List<CepResultItem> itemsList = null;
    private Context mContext = null;
    private CepListClickListener mClickListener = null;

    public ResultItemAdapter(List<CepResultItem> itemsList, Context context, CepListClickListener clickListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CepResultItemBinding binding = CepResultItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        RecyclerView.ViewHolder viewHolder = new ItemViewHolder(binding, mClickListener);
        return (ItemViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    //Adapter ViewHolder
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CepResultItemBinding binding;
        private CepListClickListener mClickListener;

        public ItemViewHolder(CepResultItemBinding binding, CepListClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.mClickListener = clickListener;
        }

        public void bind(CepResultItem item) {

            binding.cepResultItemCepTxtview.setText(item.getCep());
            binding.cepResultItemAddressTxtview.setText(item.getAddress());
            binding.cepResultItemComplTxtview.setText(item.getCompl());
            binding.cepResultItemDistrictTxtview.setText(item.getDistrict());
            binding.cepResultItemCityTxtview.setText(item.getCity());
            binding.cepResultItemAddressTxtview.setText(item.getAddress());
            binding.cepResultDeleteBtn.setOnClickListener(
                    v -> {
                        mClickListener.onClick(item);
                    }
            );

        }
    }

    public interface CepListClickListener {
        void onClick(CepResultItem clickedItem);
    }
}
