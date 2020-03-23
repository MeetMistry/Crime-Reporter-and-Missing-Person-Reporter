package com.example.crimereporter.ui.missingperson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MissingPersonViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MissingPersonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}