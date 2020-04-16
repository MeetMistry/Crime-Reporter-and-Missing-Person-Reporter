package com.example.crimereporter.ui.missingperson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.crimereporter.R;

public class MissingPersonFragment extends Fragment {

    private MissingPersonViewModel missingPersonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        missingPersonViewModel =
                ViewModelProviders.of(this).get(MissingPersonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_missingperson, container, false);
        final TextView textView = root.findViewById(R.id.text_missingperson);
        missingPersonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
