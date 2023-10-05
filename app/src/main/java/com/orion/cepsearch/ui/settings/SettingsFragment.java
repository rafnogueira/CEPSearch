package com.orion.cepsearch.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.orion.cepsearch.core.model.local.CepResultItem;
import com.orion.cepsearch.databinding.FragmentSettingsBinding;
import com.orion.cepsearch.ui.adapter.ResultItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingViewModel = null;


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

        registerObservers();

        settingViewModel.getCepList();

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

            settingViewModel.getCepLocalLiveData().observe(getViewLifecycleOwner(), cepResultItemList -> {

                binding.settingsCepsSavedRv.setLayoutManager(new LinearLayoutManager(requireContext()));

                binding.settingsCepsSavedRv.setAdapter(
                        new ResultItemAdapter(cepResultItemList, requireContext(),
                                new ResultItemAdapter.CepListClickListener(){
                                    @Override
                                    public void onClick(CepResultItem clickedItem) {

                                        Toast.makeText(requireContext(), " ", Toast.LENGTH_LONG).show();


                                    }
                                }));

            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settingViewModel.dispose(getViewLifecycleOwner());
        binding = null;
    }
}