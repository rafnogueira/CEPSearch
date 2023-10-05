package com.orion.cepsearch.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.cepsearch.R;
import com.orion.cepsearch.core.model.local.CepResult;
import com.orion.cepsearch.databinding.CepResultItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.ResultItemViewHolder> {

    private List<CepResult> itemsList;

    public ResultItemAdapter(List<CepResult> itemsList){
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ResultItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ResultItemViewHolder extends RecyclerView.ViewHolder{
        private CepResultItemBinding binding;
        public ResultItemViewHolder(CepResultItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CepResult item) {

//            binding.textView.setText(item.getText());


        }
    }


//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cep_result_item, parent, false);
//        return view;
//    }

}
