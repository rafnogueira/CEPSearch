package com.orion.cepsearch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.cepsearch.R;
import com.orion.cepsearch.core.model.local.CepResult;
import com.orion.cepsearch.databinding.CepResultItemBinding;

import java.util.List;

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.ItemViewHolder> {

    private List<CepResult> itemsList;
    private Context mContext;
    public ResultItemAdapter(List<CepResult> itemsList, Context context){
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CepResultItemBinding binding = CepResultItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        RecyclerView.ViewHolder viewHolder = new ItemViewHolder(binding);
        return (ItemViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    //Adapter ViewHolder
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private CepResultItemBinding binding;
        public ItemViewHolder(CepResultItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CepResult item) {
            binding.cepResultItemAddressTxtview.setText(item.getAddress());
        }
    }
}
