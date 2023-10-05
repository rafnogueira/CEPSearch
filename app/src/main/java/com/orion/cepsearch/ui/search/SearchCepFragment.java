package com.orion.cepsearch.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.orion.cepsearch.R;
import com.orion.cepsearch.databinding.FragmentSearchBinding;

public class SearchCepFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchCepViewModel searchCEPViewModel = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchCEPViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchCepViewModel.class);
        searchCEPViewModel.injectCepRepositoryContext(requireContext());

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.cepSearchBtn.setOnClickListener(v -> {
            searchCEPViewModel.showLoading();
            searchCEPViewModel.searchCepClick();
        });

        registerObservers();

        return root;
    }

    public void registerObservers() {
        if (searchCEPViewModel != null) {

            searchCEPViewModel.getToastMessageById().observe(getViewLifecycleOwner(), messageId -> {
                Toast.makeText(requireContext(), getString(messageId), Toast.LENGTH_SHORT).show();
            });

            searchCEPViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
                binding.cepSearchErrorLayout.setVisibility(View.VISIBLE);
                binding.cepSearchErrorLayoutLabel.setText(message);
            });

            searchCEPViewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
                if (loading) {
                    binding.cepSearchResultLayout.setVisibility(View.GONE);
                    binding.cepSearchLoadingLayout.setVisibility(View.VISIBLE);
                    binding.cepSearchErrorLayoutLabel.setText("");
                    binding.cepSearchErrorLayout.setVisibility(View.GONE);

                } else {
                    binding.cepSearchLoadingLayout.setVisibility(View.GONE);
                }
            });

            searchCEPViewModel.getResults().observe(getViewLifecycleOwner(), cepResult ->
            {
                binding.cepSearchResultLayout.setVisibility(View.VISIBLE);
                binding.cepSearchResultCepTxtview.setText(cepResult.getCep());
                binding.cepSearchResultAddressTxtview.setText(cepResult.getAddress());
                binding.cepSearchResultDistrictTxtview.setText(cepResult.getDistrict());
                binding.cepSearchResultCityTxtview.setText(cepResult.getCity());
                binding.cepSearchResultApiTxtview.setText(cepResult.getSrcApiRef());

                if (cepResult.getLng() != null
                        && cepResult.getLat() != null
                        && !cepResult.getLat().isEmpty()
                        && !cepResult.getLng().isEmpty()) {
                    binding.cepSearchResultLatTxtview.setText(cepResult.getLat());
                    binding.cepSearchResultLatTxtview.setVisibility(View.VISIBLE);
                    binding.cepSearchResultLngTxtview.setText(cepResult.getLng());
                    binding.cepSearchResultLngTxtview.setVisibility(View.VISIBLE);
                    binding.cepSearchResultMapBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.cepSearchResultLatTxtview.setText("");
                    binding.cepSearchResultLatTxtview.setVisibility(View.GONE);
                    binding.cepSearchResultLngTxtview.setText("");
                    binding.cepSearchResultLngTxtview.setVisibility(View.GONE);
                    binding.cepSearchResultMapBtn.setVisibility(View.GONE);
                }

                binding.cepSearchResultMapBtn.setOnClickListener(view -> {
                    openMap(binding.cepSearchResultLatTxtview.getText().toString(), binding.cepSearchResultLngTxtview.getText().toString());
                });

                binding.cepSearchResultSaveBtn.setOnClickListener(view -> {
                    searchCEPViewModel.sendToastMessageById(R.string.saving_address_local);
                });

                searchCEPViewModel.hideLoading();
            });
        }
    }

    private void openMap(String lat, String lng) {
        String uri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchCEPViewModel.dispose(getViewLifecycleOwner());

        binding = null;
    }

}