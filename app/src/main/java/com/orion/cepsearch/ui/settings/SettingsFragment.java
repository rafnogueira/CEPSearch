package com.orion.cepsearch.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.orion.cepsearch.core.model.local.Cep;
import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.databinding.FragmentSettingsBinding;
import com.orion.cepsearch.ui.adapter.CepItemAdapter;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingViewModel = null;

    private CepItemAdapter rvCepAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SettingsViewModel.class);
        settingViewModel.injectPrefsManagerContext(requireContext());

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        boolean manualSetting = settingViewModel.getManualSwitchSetting();
        binding.settingsManualApiSwitch.setChecked(manualSetting);
        updateSwitches(manualSetting);

        binding.settingsViaCepSwitch.setChecked(settingViewModel.getViaCepSwitchSetting());
        binding.settingsApiCepSwitch.setChecked(settingViewModel.getApiCepSwitchSetting());
        binding.settingsAwesomeCepSwitch.setChecked(settingViewModel.getAwesomeCepSwitchSetting());

        registerObservers();
        settingViewModel.refreshCepList();

        return root;
    }

    private void updateSwitches(Boolean manualSetting) {
        binding.settingsViaCepSwitch.setEnabled(manualSetting);
        binding.settingsViaCepSwitch.setChecked(manualSetting);

        binding.settingsApiCepSwitch.setEnabled(manualSetting);
        binding.settingsApiCepSwitch.setChecked(manualSetting);

        binding.settingsAwesomeCepSwitch.setEnabled(manualSetting);
        binding.settingsAwesomeCepSwitch.setChecked(manualSetting);
    }

    private void registerObservers() {
        if (settingViewModel != null) {

            settingViewModel.getManualSettingsSwitch().observe(getViewLifecycleOwner(), disableSwitchValue -> {
                updateSwitches(disableSwitchValue);
                settingViewModel.saveManualSwitchSetting(disableSwitchValue);
            });

            settingViewModel.getDeleteItemRv().observe(getViewLifecycleOwner(), deletedItem -> {

                if(rvCepAdapter != null)
                    rvCepAdapter.removeItem(deletedItem);

            });

            settingViewModel.getCepLocalLiveData().observe(getViewLifecycleOwner(), cepResultItemList -> {
                binding.settingsCepsSavedRv.setLayoutManager(new LinearLayoutManager(requireContext()));

                this.rvCepAdapter = new CepItemAdapter(cepResultItemList, requireContext(),
                        new CepItemAdapter.CepListClickListener() {
                            @Override
                            public void onClick(CepResultItem cepResultItem) {

                                settingViewModel.deleteCepItemLocal(cepResultItem);
                            }
                        });

                binding.settingsCepsSavedRv.setAdapter(rvCepAdapter);
            });

            binding.settingsManualApiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        settingViewModel.updateManualSettingsSwitch(isChecked);
                    }
            );

            binding.settingsViaCepSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        settingViewModel.saveViaCepSetting(isChecked);
                    }
            );

            binding.settingsApiCepSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        settingViewModel.saveApiCepSetting(isChecked);
                    }
            );

            binding.settingsAwesomeCepSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        settingViewModel.saveCepAwesomeSetting(isChecked);
                    }
            );

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        settingViewModel.dispose(getViewLifecycleOwner());
    }
}