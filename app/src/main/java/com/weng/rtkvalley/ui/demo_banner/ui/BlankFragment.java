package com.weng.rtkvalley.ui.demo_banner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.weng.rtkvalley.R;

public class BlankFragment extends Fragment {

    public static Fragment newInstance() {
        return new com.weng.rtkvalley.ui.demo_banner.ui.BlankFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test,null);
    }


}
