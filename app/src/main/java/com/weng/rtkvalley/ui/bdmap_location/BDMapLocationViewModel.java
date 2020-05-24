package com.weng.rtkvalley.ui.bdmap_location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BDMapLocationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BDMapLocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BD MAP fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}