package com.example.bhojnalya.ui.Sort;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SortViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SortViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Sort fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}